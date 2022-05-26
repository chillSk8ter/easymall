package com.moudle.pms.controller;


import com.moudle.pms.model.PmsCategory;
import com.moudle.pms.service.PmsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 商品三级分类 前端控制器
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@RestController
@RequestMapping("product/category")
public class PmsCategoryController {
    @Autowired
    PmsCategoryService categoryService;

    @GetMapping("/list/tree")
    public List<PmsCategory> list() {
        List<PmsCategory> listTree = categoryService.getListTree();
        return listTree;
    }

    @PostMapping("/delete")
    public R deleteCate(@RequestBody Long[] cateIds) {
        categoryService.deleteIds(cateIds);
        return R.ok();
    }

    @PostMapping("/save")
    public R saveCate(@RequestBody PmsCategory newCate) {
        categoryService.save(newCate);
        return R.ok();
    }

    @GetMapping("/info/{catId}")
    public R getCateInfo(@PathVariable("catId") String catId) {
        PmsCategory pmsCategory = categoryService.getCateInfo(catId);
        return R.ok().put("category", pmsCategory);
    }

    @PostMapping("/update")
    public R updateCateInfo(@RequestBody PmsCategory pmsCategory) {
        categoryService.updateCategoty(pmsCategory);
        return R.ok();
    }

    @PostMapping("/updateNotes")
    public R updateCateInfo(@RequestBody PmsCategory[] pmsCategories) {
        categoryService.updateBatchById(Arrays.asList(pmsCategories));
        return R.ok();
    }


}

