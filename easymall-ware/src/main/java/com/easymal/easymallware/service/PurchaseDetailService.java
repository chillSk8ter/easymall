package com.easymal.easymallware.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.easymal.easymallware.entity.PurchaseDetailEntity;
import easymall.easymallcommon.utils.PageUtils;

import java.util.Map;

/**
 * 
 *
 * @author Ethan
 * @email hongshengmo@163.com
 * @date 2020-05-27 23:15:25
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

