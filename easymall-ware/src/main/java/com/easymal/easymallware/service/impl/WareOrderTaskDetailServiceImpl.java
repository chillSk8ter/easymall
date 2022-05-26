package com.easymal.easymallware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easymal.easymallware.dao.WareOrderTaskDetailDao;
import com.easymal.easymallware.entity.WareOrderTaskDetailEntity;
import com.easymal.easymallware.service.WareOrderTaskDetailService;
import easymall.easymallcommon.utils.PageUtils;
import easymall.easymallcommon.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("wareOrderTaskDetailService")
public class WareOrderTaskDetailServiceImpl extends ServiceImpl<WareOrderTaskDetailDao, WareOrderTaskDetailEntity> implements WareOrderTaskDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareOrderTaskDetailEntity> page = this.page(
                new Query<WareOrderTaskDetailEntity>().getPage(params),
                new QueryWrapper<WareOrderTaskDetailEntity>()
        );

        return new PageUtils(page);
    }

}