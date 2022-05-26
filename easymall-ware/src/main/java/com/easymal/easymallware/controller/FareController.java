package com.easymal.easymallware.controller;

import com.easymal.easymallware.service.WareInfoService;
import com.easymal.easymallware.vo.FareVo;
import easymall.easymallcommon.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Name: FareController
 * @Author peipei
 * @Date 2022/5/1
 */
@RestController
@RequestMapping("ware/wareinfo")
public class FareController {
    @Autowired
    WareInfoService wareInfoService;

    @GetMapping("/fare/{addrId}")
    public R getFare(@PathVariable("addrId") Long addrId) {
        FareVo fareVo = wareInfoService.getFare(addrId);
        return R.ok().setData(fareVo);
    }




}
