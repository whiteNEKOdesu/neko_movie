package neko.movie.nekomovievideo.scheduled;

import lombok.extern.slf4j.Slf4j;
import neko.movie.nekomovievideo.service.DiscountInfoService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
@EnableScheduling
@EnableAsync
@Slf4j
public class DiscountExpireScheduled {
    @Resource
    private DiscountInfoService discountInfoService;

    /**
     * 使结束折扣活动过期
     */
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "0 0 */1 * * ?")
    @Async
    public void expireDiscount(){
        log.info("结束折扣活动统计定时任务启动");
        discountInfoService.expireDiscountInfo();
    }
}
