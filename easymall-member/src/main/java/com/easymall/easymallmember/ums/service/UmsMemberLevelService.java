package com.easymall.easymallmember.ums.service;

import com.easymall.easymallmember.ums.model.UmsMemberLevel;
import com.baomidou.mybatisplus.extension.service.IService;
import easymall.easymallcommon.utils.PageUtils;

import java.util.Map;

/**
 * <p>
 * 会员等级 服务类
 * </p>
 *
 * @author peipei
 * @since 2022-03-30
 */
public interface UmsMemberLevelService extends IService<UmsMemberLevel> {

    PageUtils listByCondition(Map<String, Object> params);
}
