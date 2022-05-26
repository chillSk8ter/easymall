package com.easymall.easymallmember.ums.controller;


import com.easymall.easymallmember.ums.Exception.PhoneExistException;
import com.easymall.easymallmember.ums.Exception.UserExistException;
import com.easymall.easymallmember.ums.model.UmsMember;
import com.easymall.easymallmember.ums.service.UmsMemberLevelService;
import com.easymall.easymallmember.ums.service.UmsMemberService;
import com.easymall.easymallmember.ums.vo.MemberLoginVo;
import com.easymall.easymallmember.ums.vo.MemberRegisterVo;
import com.easymall.easymallmember.ums.vo.SocialUser;
import easymall.easymallcommon.exception.BizCodeEnum;
import easymall.easymallcommon.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 会员 前端控制器
 * </p>
 *
 * @author peipei
 * @since 2022-03-30
 */
@RestController
@RequestMapping("/member/member")
public class UmsMemberController {
    @Autowired
    UmsMemberLevelService memberLevelService;

    @Autowired
    UmsMemberService memberService;

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils resPage = memberLevelService.listByCondition(params);
        return R.ok().put("page", resPage);
    }

    @PostMapping("/register")
    public R register(@RequestBody MemberRegisterVo registerVo) {
        try {
            memberService.register(registerVo);
        } catch (PhoneExistException phoneExistException) {
            return R.error(BizCodeEnum.PHONE_EXIST_EXCEPTION.getCode(), BizCodeEnum.PHONE_EXIST_EXCEPTION.getMsg());
        } catch (UserExistException userExistException) {
            return R.error(BizCodeEnum.USER_EXIST_EXCEPTION.getCode(), BizCodeEnum.USER_EXIST_EXCEPTION.getMsg());
        }
        return R.ok();
    }

    @PostMapping("/login")
    public R login(@RequestBody MemberLoginVo loginVo) {
        UmsMember loginMember = memberService.login(loginVo);
        if (loginMember != null) {
            return R.ok().put("memberEntity", loginMember);
        } else {
            return R.error(BizCodeEnum.LOGINACCT_PASSWORD_EXCEPTION.getCode(), BizCodeEnum.LOGINACCT_PASSWORD_EXCEPTION.getMsg());
        }
    }

    @RequestMapping("/oauth2/login")
    public R login(@RequestBody SocialUser socialUser) {
        UmsMember entity = memberService.login(socialUser);
        if (entity != null) {
            return R.ok().put("memberEntity", entity);
        } else {
            return R.error();
        }
    }




}

