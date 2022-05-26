package com.easymall.seckill.feign;

import easymall.easymallcommon.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("easymall-coupon")
public interface CouponFeignService {

    @GetMapping("sms/smsCoupon/seckillsession/getSeckillSessionsIn3Days")
    R getSeckillSessionsIn3Days();


}
