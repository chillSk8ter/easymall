package com.easymal.easymallware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easymal.easymallware.dao.PurchaseDetailDao;
import com.easymal.easymallware.entity.PurchaseDetailEntity;
import com.easymal.easymallware.service.PurchaseDetailService;
import easymall.easymallcommon.utils.PageUtils;
import easymall.easymallcommon.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                new QueryWrapper<PurchaseDetailEntity>()
        );

        return new PageUtils(page);
    }

}