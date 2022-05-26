package com.moudle.pms.web;

import com.moudle.pms.model.PmsCategory;
import com.moudle.pms.service.PmsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Description:
 * @Name: IndexController
 * @Author peipei
 * @Date 2022/4/11
 */
@Controller
public class IndexController {
    @Autowired
    PmsCategoryService categoryService;

    @GetMapping({"/", "index.html"})
    public String getIndex(Model model) {
        //获取所有的一级分类
        List<PmsCategory> catagories = categoryService.getLevel1Catagories();
        model.addAttribute("catagories", catagories);
        return "index";
    }


}
