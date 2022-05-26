package easymallorder.easymallorder.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import easymall.easymallcommon.utils.PageUtils;
import easymallorder.easymallorder.order.entity.PaymentInfoEntity;

import java.util.Map;

/**
 * 支付信息表
 *
 * @author Ethan
 * @email hongshengmo@163.com
 * @date 2020-05-27 23:07:28
 */
public interface PaymentInfoService extends IService<PaymentInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

