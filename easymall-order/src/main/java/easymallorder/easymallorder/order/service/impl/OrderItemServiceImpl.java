package easymallorder.easymallorder.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import easymall.easymallcommon.utils.PageUtils;
import easymall.easymallcommon.utils.Query;
import easymallorder.easymallorder.order.dao.OrderItemDao;
import easymallorder.easymallorder.order.entity.OrderItemEntity;
import easymallorder.easymallorder.order.service.OrderItemService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("orderItemService")
public class OrderItemServiceImpl extends ServiceImpl<OrderItemDao, OrderItemEntity> implements OrderItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderItemEntity> page = this.page(
                new Query<OrderItemEntity>().getPage(params),
                new QueryWrapper<OrderItemEntity>()
        );

        return new PageUtils(page);
    }

}