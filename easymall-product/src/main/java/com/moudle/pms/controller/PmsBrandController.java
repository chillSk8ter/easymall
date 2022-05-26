package com.moudle.pms.controller;


import com.moudle.pms.model.PmsBrand;
import com.moudle.pms.service.PmsBrandService;
import easymall.easymallcommon.group.UpdateGroup;
import easymall.easymallcommon.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 品牌 前端控制器
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@RestController
@RequestMapping("product/brand")
public class PmsBrandController {
    @Autowired
    PmsBrandService pmsBrandService;

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils listPages = pmsBrandService.queryPage(params);
        return R.ok().put("page", listPages);
    }

    @PostMapping("/update")
    public R update(@Validated(value = UpdateGroup.class) PmsBrand pmsBrand) {
        pmsBrandService.updateCascade(pmsBrand);
        return R.ok();
    }
}

