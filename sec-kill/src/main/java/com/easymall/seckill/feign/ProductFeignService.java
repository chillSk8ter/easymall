package com.easymall.seckill.feign;

import easymall.easymallcommon.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("easymall-product")
public interface ProductFeignService {

    @GetMapping("/product/skuinfo/info/{skuId}")
    public R getSkuInfoVo(@PathVariable("skuId") Long skuId);

}
