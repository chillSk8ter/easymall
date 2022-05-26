package com.moudle.pms.controller;


import com.moudle.pms.model.PmsSkuInfo;
import com.moudle.pms.model.PmsSpuInfo;
import com.moudle.pms.service.PmsSkuInfoService;
import easymall.easymallcommon.utils.PageUtils;
import easymall.easymallcommon.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * sku信息 前端控制器
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@RestController
@RequestMapping("/product/skuinfo")
public class PmsSkuInfoController {
    @Autowired
    PmsSkuInfoService skuInfoService;

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = skuInfoService.queryPageByCondition(params);
        return R.ok().put("page", page);
    }

    @GetMapping("/{skuId}")
    public R getSpuBySkuId(@PathVariable("skuId") Long skuId) {
        PmsSpuInfo pmsSpuInfo = skuInfoService.getSpuBySkuId(skuId);
        return R.ok().put("pmsSpuInfo", pmsSpuInfo);
    }


    @GetMapping("info/{skuId}")
    public R getSkuInfoById(@PathVariable("skuId") String skuId) {
        PmsSkuInfo byId = skuInfoService.getById(skuId);
        return R.ok().setData(byId);
    }

}

