package easymallorder.easymallorder.order.service;


import com.baomidou.mybatisplus.extension.service.IService;
import easymall.easymallcommon.utils.PageUtils;
import easymallorder.easymallorder.order.entity.RefundInfoEntity;

import java.util.Map;

/**
 * 退款信息
 *
 * @author Ethan
 * @email hongshengmo@163.com
 * @date 2020-05-27 23:07:28
 */
public interface RefundInfoService extends IService<RefundInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

