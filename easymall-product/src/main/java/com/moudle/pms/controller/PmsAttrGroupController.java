package com.moudle.pms.controller;


import com.moudle.pms.model.PmsAttr;
import com.moudle.pms.model.PmsAttrAttrgroupRelation;
import com.moudle.pms.service.PmsAttrAttrgroupRelationService;
import com.moudle.pms.service.PmsAttrGroupService;
import com.moudle.pms.service.PmsAttrService;
import com.moudle.pms.vo.PmsAttrGroupInfoVo;
import com.moudle.pms.vo.PmsCateGroupWithAttrVo;
import easymall.easymallcommon.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 属性分组 前端控制器
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@RestController
@RequestMapping("product/attrgroup")
public class PmsAttrGroupController {
    @Autowired
    PmsAttrGroupService pmsAttrGroupService;

    @Autowired
    PmsAttrAttrgroupRelationService attrgroupRelationService;

    @Autowired
    PmsAttrService pmsAttrService;

    /**
     * 获取该分组下所有属性
     *
     * @param attrgroupId
     * @return
     */
    @RequestMapping("/{attrgroupId}/attr/relation")
    public R getAttrByGroupId(@PathVariable("attrgroupId") String attrgroupId) {
        List<PmsAttr> attrByGroupId = pmsAttrGroupService.getAttrByGroupId(attrgroupId);
        return R.ok().put("date", attrByGroupId);
    }

    @GetMapping("/info/{attrGroupId}")
    public R getAttrGroupInfo(@PathVariable("attrGroupId") String attrGroupId) {
        PmsAttrGroupInfoVo pmsAttrGroupInfoVo = pmsAttrGroupService.getAttrGroupInfo(attrGroupId);
        return R.ok().put("attrGroup", pmsAttrGroupInfoVo);
    }

    @PostMapping("/attr/relation")
    public R saveBatch(@RequestBody List<PmsAttrAttrgroupRelation> attrgroupRelationList) {
        attrgroupRelationService.saveBatch(attrgroupRelationList);
        return R.ok();
    }

    @GetMapping("/{attrgroupId}/noattr/relation")
    public R attrNoRelation(@PathVariable("attrgroupId") Long attrgroupId,
                            @RequestParam Map<String, Object> params) {
        PageUtils page = pmsAttrGroupService.getNoRelationAttr(attrgroupId, params);
        return R.ok().put("page", page);
    }

    @GetMapping("/{catelogId}/withattr")
    public R cateGroupWithAttr(@PathVariable("catelogId") Long catelogId) {
        List<PmsCateGroupWithAttrVo> cateGroupWithAttr=pmsAttrGroupService.cateGroupWithAttr(catelogId);
        return R.ok().put("date",cateGroupWithAttr);
    }

}

