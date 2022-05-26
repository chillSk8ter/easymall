package com.moudle.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moudle.pms.model.PmsAttr;
import com.moudle.pms.mapper.PmsAttrMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moudle.pms.model.PmsAttrAttrgroupRelation;
import com.moudle.pms.model.PmsAttrGroup;
import com.moudle.pms.model.PmsProductAttrValue;
import com.moudle.pms.service.PmsAttrAttrgroupRelationService;
import com.moudle.pms.service.PmsAttrGroupService;
import com.moudle.pms.service.PmsAttrService;
import com.moudle.pms.service.PmsProductAttrValueService;
import com.moudle.pms.vo.PmsAttrVo;
import easymall.easymallcommon.constant.QueryConstant;
import easymall.easymallcommon.utils.PageUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
@Service
public class PmsAttrServiceImpl extends ServiceImpl<PmsAttrMapper, PmsAttr> implements PmsAttrService {
    @Autowired
    PmsAttrAttrgroupRelationService pmsAttrAttrgroupRelationService;

    @Autowired
    PmsProductAttrValueService productAttrValueService;

    @Override
    public void saveAttr(PmsAttrVo pmsAttrVo) {
        PmsAttr pmsAttr = new PmsAttr();
        BeanUtils.copyProperties(pmsAttrVo, pmsAttr);
        save(pmsAttr);
        if (pmsAttrVo.getAttrGroupId() != null) {
            PmsAttrAttrgroupRelation pmsAttrAttrgroupRelation = new PmsAttrAttrgroupRelation();
            pmsAttrAttrgroupRelation.setAttrGroupId(pmsAttrVo.getAttrGroupId());
            pmsAttrAttrgroupRelation.setAttrId(pmsAttr.getAttrId());
        }
    }

    @Transactional
    @Override
    public void updateAttr(PmsAttrVo pmsAttrVo) {
        PmsAttr pmsAttr = new PmsAttr();
        BeanUtils.copyProperties(pmsAttrVo, pmsAttr);
        updateById(pmsAttr);
        if (pmsAttr.getAttrId() != null) {
            PmsAttrAttrgroupRelation pmsAttrAttrgroupRelation = new PmsAttrAttrgroupRelation();
            pmsAttrAttrgroupRelation.setAttrId(pmsAttr.getAttrId());
            pmsAttrAttrgroupRelation.setAttrGroupId(pmsAttrVo.getAttrGroupId());
            long count = pmsAttrAttrgroupRelationService.count(new QueryWrapper<PmsAttrAttrgroupRelation>()
                    .lambda().eq(PmsAttrAttrgroupRelation::getAttrId, pmsAttrVo.getAttrId()));
            if (count > 0) {
                pmsAttrAttrgroupRelationService.update(pmsAttrAttrgroupRelation, new QueryWrapper<PmsAttrAttrgroupRelation>().lambda()
                        .eq(PmsAttrAttrgroupRelation::getAttrId, pmsAttrVo.getAttrId()));
            } else {
                pmsAttrAttrgroupRelationService.save(pmsAttrAttrgroupRelation);
            }
        }
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, String> param, Long catelogId) {
        QueryWrapper<PmsAttr> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("catelog_id", catelogId);
        if (param.get("key") != null) {
            queryWrapper.like("attr_name", param.get("key"));
        }
        if (param.get("sidx") != null && param.get("order") != null) {
            String orderCol = param.get("sidx");
            if (param.get("order").equals(QueryConstant.Order.ASC)) {
                queryWrapper.orderByAsc(orderCol);
            } else {
                queryWrapper.orderByDesc(orderCol);
            }
        }
        Page<PmsAttr> pmsAttrPage = new Page<>(Long.valueOf(param.get("page")), Long.valueOf(param.get("limit")));
        return new PageUtils(page(pmsAttrPage, queryWrapper));
    }

    @Override
    public List<PmsAttr> getAttrByGroupId(Long attrGroupId) {
        LambdaQueryWrapper<PmsAttrAttrgroupRelation> wrapper = new QueryWrapper<PmsAttrAttrgroupRelation>().lambda();
        wrapper.eq(PmsAttrAttrgroupRelation::getAttrGroupId, attrGroupId);
        List<PmsAttrAttrgroupRelation> attrgroupRelationList = pmsAttrAttrgroupRelationService.list(wrapper);
        List<Long> attrIds = attrgroupRelationList.stream()
                .map(PmsAttrAttrgroupRelation::getAttrId).collect(Collectors.toList());
        return listByIds(attrIds);
    }

    @Override
    public List<PmsProductAttrValue> listAttrsforSpu(String spuId) {
        LambdaQueryWrapper<PmsProductAttrValue> wrapper = new QueryWrapper<PmsProductAttrValue>().lambda();
        wrapper.eq(PmsProductAttrValue::getSpuId, spuId);
        List<PmsProductAttrValue> attrValueList = productAttrValueService.list(wrapper);
        return attrValueList;
    }

    @Override
    public void updateSpuAttrs(Long spuId, List<PmsProductAttrValue> attrValueEntities) {
        LambdaUpdateWrapper<PmsProductAttrValue> wrapper = new UpdateWrapper<PmsProductAttrValue>().lambda();
        wrapper.eq(PmsProductAttrValue::getSpuId, spuId);
        productAttrValueService.updateBatchById(attrValueEntities);
    }
}
