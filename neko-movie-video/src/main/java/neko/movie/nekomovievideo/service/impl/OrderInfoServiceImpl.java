package neko.movie.nekomovievideo.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import neko.movie.nekomoviecommonbase.utils.entity.*;
import neko.movie.nekomoviecommonbase.utils.exception.MemberServiceException;
import neko.movie.nekomoviecommonbase.utils.exception.NoSuchResultException;
import neko.movie.nekomoviecommonbase.utils.exception.OrderOverTimeException;
import neko.movie.nekomoviecommonbase.utils.exception.StockNotEnoughException;
import neko.movie.nekomovievideo.config.AliPayTemplate;
import neko.movie.nekomovievideo.entity.DiscountInfo;
import neko.movie.nekomovievideo.entity.OrderInfo;
import neko.movie.nekomovievideo.feign.member.MemberLevelDictFeignService;
import neko.movie.nekomovievideo.mapper.OrderInfoMapper;
import neko.movie.nekomovievideo.service.DiscountInfoService;
import neko.movie.nekomovievideo.service.OrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import neko.movie.nekomovievideo.to.AliPayTo;
import neko.movie.nekomovievideo.to.MemberLevelDictTo;
import neko.movie.nekomovievideo.to.RabbitMQMessageTo;
import neko.movie.nekomovievideo.vo.NewOrderInfoVo;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author NEKO
 * @since 2023-07-16
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {
    @Resource
    private DiscountInfoService discountInfoService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private MemberLevelDictFeignService memberLevelDictFeignService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private AliPayTemplate aliPayTemplate;

    @Resource(name = "threadPoolExecutor")
    private Executor threadPool;

    /**
     * 生成 token 保证预生成订单接口幂等性
     */
    @Override
    public String getPreOrderToken() {
        //生成 token 保证接口幂等性，并可以用来作为订单号
        String token = IdWorker.getTimeId();
        String key = Constant.VIDEO_REDIS_PREFIX + "order_id:" + StpUtil.getLoginId().toString() + token;

        stringRedisTemplate.opsForValue().setIfAbsent(key,
                token,
                1000 * 60 * 5,
                TimeUnit.MILLISECONDS);

        return token;
    }

    /**
     * 添加订单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void newOrder(NewOrderInfoVo vo) throws ExecutionException, InterruptedException {
        String orderId = vo.getToken();
        String uid = StpUtil.getLoginId().toString();
        String key = Constant.VIDEO_REDIS_PREFIX + "order_id:" + uid + orderId;
        String preOrder = stringRedisTemplate.opsForValue().get(key);
        //删除redis中订单 token
        Boolean isDelete = stringRedisTemplate.delete(key);
        //校验token合法性
        if(isDelete == null || !isDelete || !StringUtils.hasText(preOrder)){
            throw new NoSuchResultException("无此预生成订单信息");
        }

        //远程调用member微服务获取会员等级信息
        ResultObject<MemberLevelDictTo> r = memberLevelDictFeignService.memberLevelDictByMemberLevelId(vo.getMemberLevelId());
        if (!r.getResponseCode().equals(200)) {
            throw new MemberServiceException("member微服务远程调用异常");
        }
        MemberLevelDictTo memberLevelDictTo = r.getResult();
        if(memberLevelDictTo == null){
            throw new NoSuchResultException("无此会员等级id信息");
        }
        LocalDateTime now = LocalDateTime.now();

        RabbitMQMessageTo<String> rabbitMQMessageTo = RabbitMQMessageTo.generateMessage(orderId, MQMessageType.ORDER_STATUS_CHECK_TYPE);
        //在CorrelationData中设置回退消息
        CorrelationData correlationData = new CorrelationData(MQMessageType.ORDER_STATUS_CHECK_TYPE.toString());
        String jsonMessage = JSONUtil.toJsonStr(rabbitMQMessageTo);
        String notAvailable = "not available";
        correlationData.setReturned(new ReturnedMessage(new Message(jsonMessage.getBytes(StandardCharsets.UTF_8)),
                0,
                notAvailable,
                notAvailable,
                notAvailable));
        //向延迟队列发送订单号，用于超时解锁库存
        rabbitTemplate.convertAndSend(RabbitMqConstant.ORDER_EXCHANGE_NAME,
                RabbitMqConstant.ORDER_DEAD_LETTER_ROUTING_KEY_NAME,
                jsonMessage,
                correlationData);

        CompletableFuture<BigDecimal> totalPriceTask = CompletableFuture.supplyAsync(() -> {
            //计算总价
            BigDecimal cost = memberLevelDictTo.getPrice()
                    .multiply(new BigDecimal(vo.getPayLevelMonths().toString()))
                    .setScale(2, BigDecimal.ROUND_DOWN);

            if (vo.getDiscountId() != null) {
                //获取折扣信息
                DiscountInfo discountInfo = discountInfoService.getById(vo.getDiscountId());
                if (!discountInfo.getIsDelete() && !discountInfo.getIsEnd() &&
                        now.isAfter(discountInfo.getStartTime()) && now.isBefore(discountInfo.getEndTime())) {
                    //锁定库存
                    try {
                        discountInfoService.lockStock(discountInfo.getDiscountId(), orderId, 1);
                    }catch (StockNotEnoughException e){
                        return null;
                    }

                    cost = cost.multiply(new BigDecimal(discountInfo.getDiscountRate() * 0.01 + ""))
                            .setScale(2, BigDecimal.ROUND_DOWN);
                }
            }

            return cost;
        }, threadPool);

        CompletableFuture<Void> orderLogTask = totalPriceTask.thenAcceptAsync((cost) -> {
            if(cost == null){
                return;
            }

            OrderInfo orderInfo = new OrderInfo();
            BeanUtil.copyProperties(vo, orderInfo);
            orderInfo.setOrderId(orderId)
                    .setUid(uid)
                    .setCost(cost)
                    .setActualCost(cost)
                    .setRoleType(memberLevelDictTo.getRoleType())
                    .setCreateTime(now)
                    .setUpdateTime(now);

            //添加订单信息
            this.baseMapper.insert(orderInfo);
        }, threadPool);


        CompletableFuture<Void> alipayTask = totalPriceTask.thenAcceptAsync((cost) -> {
            if(cost == null){
                return;
            }

            AliPayTo aliPayTo = new AliPayTo();
            aliPayTo.setOut_trade_no(orderId)
                    .setSubject("NEKO_MOVIE")
                    //设置折扣价格
                    .setTotal_amount(cost.toString())
                    .setBody("NEKO_MOVIE");
            String alipayPageKey = Constant.VIDEO_REDIS_PREFIX + "order:" + uid + orderId + ":pay_page";
            //将支付宝支付页面信息存入 redis
            try {
                stringRedisTemplate.opsForValue().setIfAbsent(alipayPageKey,
                        aliPayTemplate.pay(aliPayTo),
                        1000 * 60 * 4,
                        TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, threadPool);

        CompletableFuture.allOf(orderLogTask, alipayTask).get();

        if(totalPriceTask.getNow(null) == null){
            throw new StockNotEnoughException("库存不足");
        }
    }

    /**
     * 根据订单号获取支付宝支付页面
     */
    @Override
    public String getAlipayPage(String orderId, String token) {
        OrderInfo orderInfo = this.baseMapper.selectById(orderId);
        if(orderInfo == null || !orderInfo.getStatus().equals(OrderStatus.UNPAID)){
            throw new OrderOverTimeException("订单超时");
        }

        String key = Constant.VIDEO_REDIS_PREFIX + "order:" + StpUtil.getLoginIdByToken(token) + orderId + ":pay_page";
        String alipayPayPage = stringRedisTemplate.opsForValue().get(key);
        if(!StringUtils.hasText(alipayPayPage)){
            throw new OrderOverTimeException("订单超时");
        }

        return alipayPayPage;
    }
}
