package com.easymall.auth.feign;

import com.easymall.auth.vo.SocialUser;
import com.easymall.auth.vo.UserLoginVo;
import com.easymall.auth.vo.UserRegisterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "easymall-member")
public interface MemberFeignService {

    @RequestMapping("member/member/register")
    R register(@RequestBody UserRegisterVo registerVo);

    @RequestMapping("member/member/login")
    R login(@RequestBody UserLoginVo loginVo);

    @RequestMapping("member/member/oauth2/login")
    R login(@RequestBody SocialUser socialUser);
}
