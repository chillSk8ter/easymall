package com.easymall.easymallmember.ums.service;

import com.easymall.easymallmember.ums.Exception.PhoneExistException;
import com.easymall.easymallmember.ums.Exception.UserExistException;
import com.easymall.easymallmember.ums.model.UmsMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easymall.easymallmember.ums.vo.MemberLoginVo;
import com.easymall.easymallmember.ums.vo.MemberRegisterVo;
import com.easymall.easymallmember.ums.vo.SocialUser;

/**
 * <p>
 * 会员 服务类
 * </p>
 *
 * @author peipei
 * @since 2022-03-30
 */
public interface UmsMemberService extends IService<UmsMember> {

    /**
     * 注册会员
     *
     * @param registerVo
     */
    void register(MemberRegisterVo registerVo);

    /**
     * 验证手机号是否唯一
     */
    void checkPhoneUnique(String phone);

    /**
     * 验证用户名是否唯一
     */
    void checkNameUnique(String userName);


    UmsMember login(MemberLoginVo loginVo);

    /**
     * 第三方登录
     *
     * @param socialUser
     * @return
     */
    UmsMember login(SocialUser socialUser);
}
