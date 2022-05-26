package com.easymal.easymallware.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easymal.easymallware.entity.WareOrderTaskEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存工作单
 * 
 * @author Ethan
 * @email hongshengmo@163.com
 * @date 2020-05-27 23:15:25
 */
@Mapper
public interface WareOrderTaskDao extends BaseMapper<WareOrderTaskEntity> {
	
}
