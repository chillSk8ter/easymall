package com.easymal.easymallware.feign;

import easymall.easymallcommon.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("easymall-order")
public interface OrderFeignService {
    @GetMapping("/order/order/infoByOrderSn/{orderSn}")
    R getInfoByOrderSn(@PathVariable("orderSn") String OrderSn);


}
