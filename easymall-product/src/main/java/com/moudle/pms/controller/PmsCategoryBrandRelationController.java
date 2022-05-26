package com.moudle.pms.controller;


import com.moudle.pms.model.PmsBrand;
import com.moudle.pms.model.PmsCategoryBrandRelation;
import com.moudle.pms.service.PmsCategoryBrandRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 品牌分类关联 前端控制器
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class PmsCategoryBrandRelationController {
    @Autowired
    PmsCategoryBrandRelationService categoryBrandRelationService;

    @GetMapping("/catelog/list")
    public R cateloglist(@RequestParam("brandId") Long brandId) {
        List<PmsCategoryBrandRelation> categoryBrandRelationList = categoryBrandRelationService.catelogList(brandId);
        return R.ok().put("data", categoryBrandRelationList);
    }

    @PostMapping
    public R save(@RequestBody PmsCategoryBrandRelation pmsCategoryBrandRelation) {
        categoryBrandRelationService.saveDetail(pmsCategoryBrandRelation);
        return R.ok();
    }

    @GetMapping("/brands/list/{catId}")
    public R list(@PathVariable("catId") Long catId) {
        List<PmsBrand> pmsBrands=categoryBrandRelationService.getBrandByCateId(catId);
        return R.ok().put("data",pmsBrands);
    }

    @GetMapping("/catelog/list/{brandId}")
    public R listByBrandId(@PathVariable("brandId") Long brandId) {
        List<PmsCategoryBrandRelation> listByBrandId=categoryBrandRelationService.listByBrandId(brandId);
        return R.ok().put("data",listByBrandId);
    }








}

