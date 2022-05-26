package com.easymal.easymallware.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.easymal.easymallware.entity.WareOrderTaskDetailEntity;
import easymall.easymallcommon.utils.PageUtils;

import java.util.Map;

/**
 * 库存工作单
 *
 * @author Ethan
 * @email hongshengmo@163.com
 * @date 2020-05-27 23:15:25
 */
public interface WareOrderTaskDetailService extends IService<WareOrderTaskDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

