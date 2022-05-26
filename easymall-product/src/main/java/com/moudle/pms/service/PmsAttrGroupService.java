package com.moudle.pms.service;

import com.moudle.pms.model.PmsAttr;
import com.moudle.pms.model.PmsAttrGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.moudle.pms.vo.PmsAttrGroupInfoVo;
import com.moudle.pms.vo.PmsCateGroupWithAttrVo;
import easymall.easymallcommon.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 属性分组 服务类
 * </p>
 *
 * @author peipei
 * @since 2022-03-17
 */
public interface PmsAttrGroupService extends IService<PmsAttrGroup> {

    /**
     * 条件分页查询
     * @param params
     * @param catelogId
     * @return
     */
    PageUtils queryPage(Map<String, Object> params, long catelogId);

    /**
     * 获取该分组下所有属性
     * @param attrgroupId
     * @return
     */
    List<PmsAttr> getAttrByGroupId(String attrgroupId);

    /**
     *获取属性分组详情信息
     * @param attrGroupId
     * @return
     */
    PmsAttrGroupInfoVo getAttrGroupInfo(String attrGroupId);

    /**
     * 获取属性分组里面还没有关联的本分类里面的其他基本属性，方便添加新的关联
     * @param attrgroupId
     * @param params
     * @return
     */
    PageUtils getNoRelationAttr(Long attrgroupId, Map<String, Object> params);

    List<PmsCateGroupWithAttrVo> cateGroupWithAttr(Long catelogId);

}
