package neko.movie.nekomovievideo.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
import neko.movie.nekomovievideo.to.AliPayTo;
import neko.movie.nekomovievideo.to.MemberLevelDictTo;
import neko.movie.nekomovievideo.to.RabbitMQMessageTo;
import neko.movie.nekomovievideo.to.UpdateMemberLevelTo;
import neko.movie.nekomovievideo.vo.AliPayAsyncVo;
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
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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
@Slf4j
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

        //获取折扣信息
        DiscountInfo discountInfo = discountInfoService.getById(vo.getDiscountId());
        //判断折扣活动是否有效
        if (discountInfo == null || discountInfo.getIsDelete() || discountInfo.getIsEnd() ||
                now.isBefore(discountInfo.getStartTime()) || now.isAfter(discountInfo.getEndTime())) {
            discountInfo = null;
            vo.setDiscountId(null);
        }

        DiscountInfo finalDiscountInfo = discountInfo;
        CompletableFuture<BigDecimal> totalPriceTask = CompletableFuture.supplyAsync(() -> {
            //计算总价
            BigDecimal cost = memberLevelDictTo.getPrice()
                    .multiply(new BigDecimal(vo.getPayLevelMonths().toString()))
                    .setScale(2, BigDecimal.ROUND_DOWN);

            if (finalDiscountInfo != null) {
                //锁定库存
                try {
                    discountInfoService.lockStock(finalDiscountInfo.getDiscountId(), orderId, 1);
                }catch (StockNotEnoughException e){
                    return null;
                }

                cost = cost.multiply(new BigDecimal(finalDiscountInfo.getDiscountRate() * 0.01 + ""))
                        .setScale(2, BigDecimal.ROUND_DOWN);
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

    /**
     * 支付宝异步支付通知处理
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String alipayTradeCheck(AliPayAsyncVo vo, HttpServletRequest request) throws AlipayApiException {
        //验签
        Map<String,String> params = new HashMap<>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        //调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(params,
                aliPayTemplate.getAlipayPublicKey(),
                aliPayTemplate.getCharset(),
                aliPayTemplate.getSignType());

        if(signVerified){
            if(vo.getTrade_status().equals("TRADE_SUCCESS") || vo.getTrade_status().equals("TRADE_FINISHED")){
                String orderId = vo.getOut_trade_no();
                OrderInfo orderInfo = this.baseMapper.selectById(orderId);
                if(orderInfo == null){
                    log.error("订单号: " + orderId + "，支付宝流水号: " + vo.getTrade_no() + "，订单不存在");
                    return "error";
                }

                OrderInfo todoUpdate = new OrderInfo();
                LocalDateTime now = LocalDateTime.now();
                todoUpdate.setOrderId(orderId)
                        .setAlipayTradeId(vo.getTrade_no())
                        .setStatus(OrderStatus.PAID)
                        .setUpdateTime(now);

                //修改订单状态信息
                this.baseMapper.updateById(todoUpdate);

                //折扣id不为空，则解锁库存并扣除库存
                if(orderInfo.getDiscountId() != null){
                    discountInfoService.confirmLockStockPay(orderId);
                }

                //组装修改会员等级队列消息to
                UpdateMemberLevelTo updateMemberLevelTo = new UpdateMemberLevelTo();
                updateMemberLevelTo.setUid(orderInfo.getUid())
                        .setMemberLevelId(orderInfo.getMemberLevelId())
                        .setPayLevelMonths(orderInfo.getPayLevelMonths());

                RabbitMQMessageTo<UpdateMemberLevelTo> rabbitMQMessageTo = RabbitMQMessageTo.generateMessage(updateMemberLevelTo,
                        MQMessageType.MEMBER_LEVEL_UPDATE_TYPE);
                //在CorrelationData中设置回退消息
                CorrelationData correlationData = new CorrelationData(MQMessageType.MEMBER_LEVEL_UPDATE_TYPE.toString());
                String jsonMessage = JSONUtil.toJsonStr(rabbitMQMessageTo);
                String notAvailable = "not available";
                correlationData.setReturned(new ReturnedMessage(new Message(jsonMessage.getBytes(StandardCharsets.UTF_8)),
                        0,
                        notAvailable,
                        notAvailable,
                        notAvailable));
                //向修改会员等级队列发送消息
                rabbitTemplate.convertAndSend(RabbitMqConstant.MEMBER_LEVEL_UPDATE_EXCHANGE_NAME,
                        RabbitMqConstant.MEMBER_LEVEL_UPDATE_QUEUE_ROUTING_KEY_NAME,
                        jsonMessage,
                        correlationData);

                log.info("订单号: " + orderId + "，支付宝流水号: " + vo.getTrade_no() + "，订单支付确认完成");
            }

            return "success";
        }else{
            return "error";
        }
    }

    /**
     * 根据订单号修改订单状态为取消状态
     */
    @Override
    public void updateOrderInfoStatusToCancel(String orderId) {
        this.baseMapper.updateOrderInfoStatusToCancel(orderId, LocalDateTime.now());
    }

    /**
     * 根据订单号获取未取消订单信息
     */
    @Override
    public OrderInfo getUncanceledOrderInfoByOrderId(String orderId) {
        return this.baseMapper.selectOne(new QueryWrapper<OrderInfo>().lambda()
                .eq(OrderInfo::getOrderId, orderId)
                .ne(OrderInfo::getStatus, OrderStatus.CANCELED));
    }
}
