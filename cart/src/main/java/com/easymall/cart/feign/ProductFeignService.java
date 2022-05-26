package com.easymall.cart.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description:
 * @Name: ProductFeignService
 * @Author peipei
 * @Date 2022/4/26
 */
@FeignClient("easy-product")
public interface ProductFeignService {

    @GetMapping("/product/skuinfo/info/{skuId}")
    R info(@RequestParam("skuId") Long skuId);

    @RequestMapping("product/skusaleattrvalue/getSkuSaleAttrValuesAsString")
    List<String> getSkuSaleAttrValuesAsString(@RequestBody Long skuId);

}
