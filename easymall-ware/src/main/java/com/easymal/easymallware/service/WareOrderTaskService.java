package com.easymal.easymallware.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.easymal.easymallware.entity.WareOrderTaskEntity;
import easymall.easymallcommon.utils.PageUtils;

import java.util.Map;

/**
 * 库存工作单
 *
 * @author Ethan
 * @email hongshengmo@163.com
 * @date 2020-05-27 23:15:25
 */
public interface WareOrderTaskService extends IService<WareOrderTaskEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

