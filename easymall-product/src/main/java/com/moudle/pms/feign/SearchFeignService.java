package com.moudle.pms.feign;

import easymall.easymallcommon.to.es.SkuEsModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("/search")
public interface SearchFeignService {

    @PostMapping("/product")
    public R saveProductAsIndices(@RequestBody List<SkuEsModel> skuEsModelList);


}
