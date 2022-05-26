package com.moudle.pms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moudle.pms.mapper.PmsAttrGroupMapper;
import com.moudle.pms.model.PmsAttr;
import com.moudle.pms.model.PmsAttrAttrgroupRelation;
import com.moudle.pms.model.PmsAttrGroup;
import com.moudle.pms.model.PmsCategory;
import com.moudle.pms.service.PmsAttrAttrgroupRelationService;
import com.moudle.pms.service.PmsAttrGroupService;
import com.moudle.pms.service.PmsAttrService;
import com.moudle.pms.service.PmsCategoryService;
import com.moudle.pms.vo.PmsAttrGroupInfoVo;
import com.moudle.pms.vo.PmsCateGroupWithAttrVo;
import easymall.easymallcommon.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 属性分组 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@Service
public class PmsAttrGroupServiceImpl extends ServiceImpl<PmsAttrGroupMapper, PmsAttrGroup> implements PmsAttrGroupService {
    @Autowired
    PmsAttrGroupMapper pmsAttrGroupMapper;

    @Autowired
    PmsAttrAttrgroupRelationService attrAttrgroupRelationService;

    @Autowired
    PmsAttrService attrService;

    @Autowired
    PmsCategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params, long catelogId) {
        String key = (String) params.get("key");
        LambdaQueryWrapper<PmsAttrGroup> lambdaQueryWrapper = new QueryWrapper<PmsAttrGroup>().lambda();
        if (StrUtil.isNotEmpty(key)) {
            lambdaQueryWrapper.like(PmsAttrGroup::getAttrGroupName, key)
                    .or().like(PmsAttrGroup::getDescript, key);
        }
        if (catelogId != 0) {
            lambdaQueryWrapper.eq(PmsAttrGroup::getCatelogId, catelogId);
        }
        IPage<PmsAttrGroup> page = new Page<>();
        IPage<PmsAttrGroup> resPage = pmsAttrGroupMapper.selectPage(page, lambdaQueryWrapper);
        return new PageUtils(resPage);
    }

    @Transactional
    @Override
    public List<PmsAttr> getAttrByGroupId(String attrgroupId) {
        List<PmsAttrAttrgroupRelation> relationList = attrAttrgroupRelationService.list(new QueryWrapper<PmsAttrAttrgroupRelation>().lambda()
                .eq(PmsAttrAttrgroupRelation::getAttrGroupId, attrgroupId));
        List<Long> attrIdList = relationList.stream().map(PmsAttrAttrgroupRelation::getAttrId).collect(Collectors.toList());
        return attrService.listByIds(attrIdList);
    }

    @Override
    public PmsAttrGroupInfoVo getAttrGroupInfo(String attrGroupId) {
        PmsAttrGroupInfoVo attrGroupInfoVo = new PmsAttrGroupInfoVo();
        PmsAttrGroup pmsAttrGroup = getById(attrGroupId);
        BeanUtils.copyProperties(pmsAttrGroup, attrGroupInfoVo);
        attrGroupInfoVo.setCatelogPath(categoryService.getCateLogPathById(pmsAttrGroup.getCatelogId()));
        return attrGroupInfoVo;
    }

    @Override
    public PageUtils getNoRelationAttr(Long attrgroupId, Map<String, Object> params) {
        PmsAttrGroup pmsAttrGroup = getById(attrgroupId);
        LambdaQueryWrapper<PmsAttr> lambdaQueryWrapper = new QueryWrapper<PmsAttr>().lambda();
        lambdaQueryWrapper.eq(PmsAttr::getCatelogId, pmsAttrGroup.getAttrGroupId())
                .eq(PmsAttr::getSearchType, "0");
        String key = (String) params.get("key");
        if (StrUtil.isNotBlank((String) params.get("key"))) {
            lambdaQueryWrapper.eq(PmsAttr::getAttrName, key);
        }
        IPage<PmsAttr> attrPage = new Page<>((Long) params.get("page"), (Long) params.get("limit"));
        List<PmsAttr> records = attrPage.getRecords();
        ArrayList<PmsAttr> resAttrs = new ArrayList<>();
        for (PmsAttr record : records) {
            long count = attrAttrgroupRelationService.count(new QueryWrapper<PmsAttrAttrgroupRelation>().lambda()
                    .eq(PmsAttrAttrgroupRelation::getAttrId, record.getAttrId()));
            if (count == 0) {
                resAttrs.add(record);
            }
        }
        return new PageUtils(resAttrs, attrPage.getCurrent(), attrPage.getTotal(), attrPage.getSize());
    }

    @Override
    public List<PmsCateGroupWithAttrVo> cateGroupWithAttr(Long catelogId) {
        LambdaQueryWrapper<PmsAttrGroup> queryWrapper = new QueryWrapper<PmsAttrGroup>().lambda();
        queryWrapper.eq(PmsAttrGroup::getCatelogId,catelogId);
        List<PmsAttrGroup> pmsAttrGroupList = list(queryWrapper);
        ArrayList<PmsCateGroupWithAttrVo> cateGroupWithAttrVos = new ArrayList<>();
        for (PmsAttrGroup pmsAttrGroup : pmsAttrGroupList) {
            PmsCateGroupWithAttrVo cateGroupWithAttrVo = new PmsCateGroupWithAttrVo();
            BeanUtils.copyProperties(pmsAttrGroup,cateGroupWithAttrVo);
            List<PmsAttr> attrsByGroupId=attrService.getAttrByGroupId(pmsAttrGroup.getAttrGroupId());
            cateGroupWithAttrVo.setPmsAttrList(attrsByGroupId);
            cateGroupWithAttrVos.add(cateGroupWithAttrVo);
        }
        return cateGroupWithAttrVos;
    }
}
