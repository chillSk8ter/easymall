package com.easymall.easymallmember.ums.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easymall.easymallmember.ums.mapper.MemberReceiveAddressDao;
import com.easymall.easymallmember.ums.model.MemberReceiveAddressEntity;
import com.easymall.easymallmember.ums.service.MemberReceiveAddressService;
import easymall.easymallcommon.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("memberReceiveAddressService")
public class MemberReceiveAddressServiceImpl extends ServiceImpl<MemberReceiveAddressDao, MemberReceiveAddressEntity> implements MemberReceiveAddressService {

    @Override
    public List<MemberReceiveAddressEntity> getAddressByUserId(Long userId) {
        LambdaQueryWrapper<MemberReceiveAddressEntity> wrapper = new QueryWrapper<MemberReceiveAddressEntity>().lambda();
        wrapper.eq(MemberReceiveAddressEntity::getMemberId, userId);
        return list(wrapper);
    }

    @Override
    public MemberReceiveAddressEntity getAddress(Long id) {
        MemberReceiveAddressEntity addressEntity = getById(id);
        return addressEntity;
    }
}