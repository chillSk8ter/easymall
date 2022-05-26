package com.moudle.pms.controller;


import com.moudle.pms.model.PmsProductAttrValue;
import com.moudle.pms.service.PmsAttrService;
import com.moudle.pms.vo.PmsAttrVo;
import easymall.easymallcommon.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品属性 前端控制器
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@RestController
@RequestMapping("product/attr")
public class PmsAttrController {
    @Autowired
    PmsAttrService pmsAttrService;

    @PostMapping("/save")
    public R save(@RequestBody PmsAttrVo pmsAttrVo) {
        pmsAttrService.saveAttr(pmsAttrVo);
        return R.ok();
    }

    @PostMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        pmsAttrService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

    @PostMapping("/update")
    public R update(@RequestBody PmsAttrVo pmsAttrVo) {
        pmsAttrService.updateAttr(pmsAttrVo);
        return R.ok();
    }

    @GetMapping("/base/list/{catelogId}")
    public R baseAttrList(@RequestParam Map<String, String> param,
                          @PathVariable("catelogId") Long catelogId) {
        PageUtils pageUtils = pmsAttrService.queryBaseAttrPage(param, catelogId);
        return R.ok().put("page", pageUtils);
    }

    @GetMapping("/base/listforspu/{spuId}")
    public R listAttrsforSpu(@PathVariable("spuId") String spuId) {
        List<PmsProductAttrValue> productAttrValueList = pmsAttrService.listAttrsforSpu(spuId);
        return R.ok().put("data", productAttrValueList);
    }

    @PostMapping("/update/{spuId}")
    public R updateSpuAttrs(@PathVariable("spuId") Long spuId, @RequestBody List<PmsProductAttrValue> attrValueEntities) {
        pmsAttrService.updateSpuAttrs(spuId, attrValueEntities);
        return R.ok();
    }

}

