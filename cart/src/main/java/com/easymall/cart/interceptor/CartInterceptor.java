package com.easymall.cart.interceptor;

import com.easymall.cart.to.UserInfoTo;
import easymall.easymallcommon.constant.AuthServerConstant;
import easymall.easymallcommon.constant.CartConstant;
import easymall.easymallcommon.vo.MemberResponseVo;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @Description:
 * @Name: CartInterceptor
 * @Author peipei
 * @Date 2022/4/25
 */
@Component
public class CartInterceptor implements HandlerInterceptor {
    public static final ThreadLocal<UserInfoTo> threadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        MemberResponseVo memberResponseVo = (MemberResponseVo) session.getAttribute(AuthServerConstant.LOGIN_USER);
        UserInfoTo userInfoTo = new UserInfoTo();
        if (memberResponseVo != null) {
            userInfoTo.setUserId(memberResponseVo.getId());
        }
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(CartConstant.TEMP_USER_COOKIE_NAME)) {
                userInfoTo.setUserKey(cookie.getValue());
                userInfoTo.setTempUser(true);
            }
        }

        if (StringUtils.isEmpty(userInfoTo.getUserKey())) {
            String uuidStr = UUID.randomUUID().toString();
            userInfoTo.setUserKey(uuidStr);
        }
        threadLocal.set(userInfoTo);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserInfoTo userInfoTo = threadLocal.get();
        if (!userInfoTo.getTempUser()) {
            Cookie cookie = new Cookie(CartConstant.TEMP_USER_COOKIE_NAME, userInfoTo.getUserKey());
            cookie.setDomain("easymall.com");
            cookie.setMaxAge(CartConstant.TEMP_USER_COOKIE_TIMEOUT);
            response.addCookie(cookie);
        }
    }
}
