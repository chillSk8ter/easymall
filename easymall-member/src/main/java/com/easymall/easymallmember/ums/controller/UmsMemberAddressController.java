package com.easymall.easymallmember.ums.controller;

import com.easymall.easymallmember.ums.model.MemberReceiveAddressEntity;
import com.easymall.easymallmember.ums.service.MemberReceiveAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:
 * @Name: UmsMemberAddressController
 * @Author peipei
 * @Date 2022/4/30
 */
@RestController
@RequestMapping("/member/memberreceiveaddress")
public class UmsMemberAddressController {
    @Autowired
    MemberReceiveAddressService memberReceiveAddressService;


    @GetMapping("/getAddressByUserId")
    public List<MemberReceiveAddressEntity> getAddressByUserId(@RequestParam("userId") Long userId) {
        List<MemberReceiveAddressEntity> list = memberReceiveAddressService.getAddressByUserId(userId);
        return list;
    }

    @GetMapping("/info/{id}")
    public R getAddress(@PathVariable("id") Long id) {
        MemberReceiveAddressEntity addressEntity = memberReceiveAddressService.getAddress(id);
        return R.ok().put("memberReceiveAddress", addressEntity);
    }


}
