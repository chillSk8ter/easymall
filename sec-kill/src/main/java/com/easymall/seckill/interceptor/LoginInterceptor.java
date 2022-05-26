package com.easymall.seckill.interceptor;

import easymall.easymallcommon.constant.AuthServerConstant;
import easymall.easymallcommon.vo.MemberResponseVo;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.Authenticator;

/**
 * @Description:
 * @Name: LoginInterceptor
 * @Author peipei
 * @Date 2022/5/8
 */
@Configuration
public class LoginInterceptor implements HandlerInterceptor {
    public static ThreadLocal<MemberResponseVo> threadLocal = new ThreadLocal<MemberResponseVo>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MemberResponseVo memberResponseVo = (MemberResponseVo) request.getSession().getAttribute(AuthServerConstant.LOGIN_USER);
        if (memberResponseVo == null) {
            return false;
        } else {
            threadLocal.set(memberResponseVo);
            return true;
        }

    }
}
