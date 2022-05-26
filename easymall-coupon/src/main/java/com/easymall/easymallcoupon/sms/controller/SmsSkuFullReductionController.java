package com.easymall.easymallcoupon.sms.controller;


import com.easymall.easymallcoupon.sms.service.SmsSkuFullReductionService;
import easymall.easymallcommon.to.SkuReductionTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品满减信息 前端控制器
 * </p>
 *
 * @author peipei
 * @since 2022-04-02
 */
@RestController
@RequestMapping("coupon/skufullreduction")
public class SmsSkuFullReductionController {
    @Autowired
    SmsSkuFullReductionService skuFullReductionService;

    @PostMapping("/saveInfo")
    public R saveSkuReductionTo(@RequestBody SkuReductionTo skuReductionTo) {
        skuFullReductionService.saveSkuReductionTo(skuReductionTo);
        return R.ok();
    }


}


