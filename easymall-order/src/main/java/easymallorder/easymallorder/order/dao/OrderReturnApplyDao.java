package easymallorder.easymallorder.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import easymallorder.easymallorder.order.entity.OrderReturnApplyEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单退货申请
 * 
 * @author Ethan
 * @email hongshengmo@163.com
 * @date 2020-05-27 23:07:28
 */
@Mapper
public interface OrderReturnApplyDao extends BaseMapper<OrderReturnApplyEntity> {
	
}
