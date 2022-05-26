package com.easymall.easymallmember.ums.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.easymall.easymallmember.ums.model.MemberReceiveAddressEntity;
import easymall.easymallcommon.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 会员收货地址
 *
 * @author Ethan
 * @email hongshengmo@163.com
 * @date 2020-05-27 23:01:00
 */
public interface MemberReceiveAddressService extends IService<MemberReceiveAddressEntity> {

    /**
     * 获取用户收获信息
     *
     * @param userId
     * @return
     */
    List<MemberReceiveAddressEntity> getAddressByUserId(Long userId);

    /**
     * 根据id获得用户地址实体
     *
     * @param id
     * @return
     */
    MemberReceiveAddressEntity getAddress(Long id);
}

