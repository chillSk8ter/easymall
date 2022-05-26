package com.moudle.pms.controller;


import com.moudle.pms.service.PmsSpuInfoService;
import com.moudle.pms.vo.SpuSaveVo;
import easymall.easymallcommon.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 * spu信息 前端控制器
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@RestController
@RequestMapping("/pms/pmsSpuInfo")
public class PmsSpuInfoController {
    @Autowired
    PmsSpuInfoService pmsSpuInfoService;

    @GetMapping("/list")
    public R list(@RequestParam HashMap<String, Object> params) {
        PageUtils queryPageByCondition = pmsSpuInfoService.queryPageByCondition(params);
        return R.ok().put("page", queryPageByCondition);
    }

    @GetMapping("/save")
    public R save(@RequestBody SpuSaveVo spuSaveVo) {
        pmsSpuInfoService.saveSpuInfo(spuSaveVo);
        return R.ok();
    }

    @GetMapping("{spuId}/up")
    public R upSpuForSearch(@PathVariable("spuId") String spuId) {
        pmsSpuInfoService.upSpuForSearch(spuId);
        return R.ok();
    }


}

