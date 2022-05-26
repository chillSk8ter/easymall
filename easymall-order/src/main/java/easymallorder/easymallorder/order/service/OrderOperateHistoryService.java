package easymallorder.easymallorder.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import easymall.easymallcommon.utils.PageUtils;
import easymallorder.easymallorder.order.entity.OrderOperateHistoryEntity;

import java.util.Map;

/**
 * 订单操作历史记录
 *
 * @author Ethan
 * @email hongshengmo@163.com
 * @date 2020-05-27 23:07:28
 */
public interface OrderOperateHistoryService extends IService<OrderOperateHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

