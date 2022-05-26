package com.moudle.pms.service;

import com.moudle.pms.model.PmsAttr;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moudle.pms.model.PmsProductAttrValue;
import com.moudle.pms.vo.PmsAttrVo;
import easymall.easymallcommon.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
public interface PmsAttrService extends IService<PmsAttr> {

    /**
     * 新增分类属性
     *
     * @param pmsAttrVo
     */
    void saveAttr(PmsAttrVo pmsAttrVo);

    /**
     * 更新分类属性
     *
     * @param pmsAttrVo
     */
    void updateAttr(PmsAttrVo pmsAttrVo);

    PageUtils queryBaseAttrPage(Map<String, String> param, Long catelogId);

    /**
     * 通过属性分组id或获取属性实体
     *
     * @param attrGroupId
     * @return
     */
    List<PmsAttr> getAttrByGroupId(Long attrGroupId);

    List<PmsProductAttrValue> listAttrsforSpu(String attrId);

    void updateSpuAttrs(Long spuId, List<PmsProductAttrValue> attrValueEntities);

}
