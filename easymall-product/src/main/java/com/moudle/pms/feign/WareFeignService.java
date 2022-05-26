package com.moudle.pms.feign;

import easymall.easymallcommon.to.SkuHasStockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Description:
 * @Name: WareFeighService
 * @Author peipei
 * @Date 2022/4/6
 */
@FeignClient("easy-ware")
public interface WareFeignService {

    @GetMapping("/ware/waresku/getSkuHasStocks")
    public List<SkuHasStockVo> getSkuStockVos(List<Long> skuIds);


}
