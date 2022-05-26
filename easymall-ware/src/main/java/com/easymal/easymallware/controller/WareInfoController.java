package com.easymal.easymallware.controller;

import com.easymal.easymallware.entity.WareInfoEntity;
import com.easymal.easymallware.service.WareInfoService;
import com.easymal.easymallware.vo.FareVo;
import com.easymal.easymallware.vo.WareSkuLockVo;
import easymall.easymallcommon.utils.PageUtils;
import easymall.easymallcommon.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 仓库信息
 *
 * @author Ethan
 * @email hongshengmo@163.com
 * @date 2020-05-27 23:15:25
 */
@RestController
@RequestMapping("ware/wareinfo")
public class WareInfoController {
    @Autowired
    private WareInfoService wareInfoService;


    @RequestMapping("/fare/{addrId}")
    public FareVo getFare(@PathVariable("addrId") Long addrId) {
        return wareInfoService.getFare(addrId);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wareInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		WareInfoEntity wareInfo = wareInfoService.getById(id);

        return R.ok().put("wareInfo", wareInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody WareInfoEntity wareInfo){
		wareInfoService.save(wareInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody WareInfoEntity wareInfo){
		wareInfoService.updateById(wareInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		wareInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }




}
