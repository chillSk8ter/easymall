package com.easymall.easymallmember.ums.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easymall.easymallmember.ums.model.UmsMemberLevel;
import com.easymall.easymallmember.ums.mapper.UmsMemberLevelMapper;
import com.easymall.easymallmember.ums.service.UmsMemberLevelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import easymall.easymallcommon.utils.PageUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 会员等级 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2022-03-30
 */
@Service
public class UmsMemberLevelServiceImpl extends ServiceImpl<UmsMemberLevelMapper, UmsMemberLevel> implements UmsMemberLevelService {

    @Override
    public PageUtils listByCondition(Map<String, Object> params) {
        LambdaQueryWrapper<UmsMemberLevel> wrapper = new QueryWrapper<UmsMemberLevel>().lambda();
        IPage<UmsMemberLevel> prePage = new Page<UmsMemberLevel>(params.get("size"), params.get("limit"));
        if (params.get("key") != null) {
            wrapper.eq(UmsMemberLevel::getName, (String) params.get("key"));
        }
        IPage<UmsMemberLevel> resPage = page(prePage, wrapper);
        return new PageUtils(resPage);
    }
}
