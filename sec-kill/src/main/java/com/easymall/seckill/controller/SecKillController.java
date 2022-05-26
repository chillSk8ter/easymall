package com.easymall.seckill.controller;

import com.easymall.seckill.seckillService.SecKillService;
import com.easymall.seckill.to.SeckillSkuRedisTo;
import easymall.easymallcommon.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SecKillController {
    @Autowired
    SecKillService secKillService;

    @GetMapping("/currentSecKillSkus")
    public R getCurrentSecKillSkus() {
        List<SeckillSkuRedisTo> currentSecKillSkus = secKillService.getCurrentSecKillSkus();
        return R.ok().setData(currentSecKillSkus);
    }

    @GetMapping("/{skuId}")
    public R getSecKillSkuById(@PathVariable("skuId") String skuId) {
        SeckillSkuRedisTo skuById = secKillService.getSecKillSkuById(skuId);
        return R.ok().setData(skuById);
    }

    @GetMapping("/secKill")
    public R secKill(@RequestParam("num") Integer num
            , @RequestParam("killId") Long killId, @RequestParam("key") String key) {
        String orderSn = null;
        orderSn = secKillService.secKill(killId, num, key);
        return R.ok().setData(orderSn);

    }


}
