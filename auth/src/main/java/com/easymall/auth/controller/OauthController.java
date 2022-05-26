package com.easymall.auth.controller;

import com.alibaba.fastjson.JSON;
import com.easymall.auth.feign.MemberFeignService;
import com.easymall.auth.vo.SocialUser;
import easymall.easymallcommon.constant.AuthServerConstant;
import easymall.easymallcommon.utils.HttpUtils;
import easymall.easymallcommon.vo.MemberResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @Description:
 * @Name: OauthController
 * @Author peipei
 * @Date 2022/4/25
 */
@Slf4j
@RestController
public class OauthController {
    @Autowired
    MemberFeignService memberFeignService;

    @RequestMapping("/oauth2.0/weibo/success")
    public String authorize(String code, HttpSession session) throws Exception {
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put("client_id", "2144471074");
        queryMap.put("client_secret", "ff63a0d8d591a85a29a19492817316ab");
        queryMap.put("grant_type", "authorization_code");
        queryMap.put("redirect_uri", "http://auth.gulimall.com/oauth2.0/weibo/success");
        queryMap.put("code", code);
        HashMap<String, String> errorMap = new HashMap<>();
        HttpResponse response = HttpUtils.doPost("https://api.weibo.com", "/oauth2/access_token", "post", new HashMap<String, String>(), query, new HashMap<String, String>());
        if (response.getStatusLine().getStatusCode() == 200) {
            String jsonEntity = EntityUtils.toString(response.getEntity());
            SocialUser socialUser = JSON.parseObject(jsonEntity, SocialUser.class);
            R login = memberFeignService.login(socialUser);
            if (login.getCode() == 0) {
                String jsonString = JSON.toJSONString(login.get("memberEntity"));
                MemberResponseVo memberResponseVo = JSON.parseObject(jsonString, MemberResponseVo.class);
                session.setAttribute(AuthServerConstant.LOGIN_USER, memberResponseVo);
                return null;
            } else {
                errorMap.put("msg", "生成登录失败");
                session.setAttribute("errors", errorMap);
                return null;
            }
        } else {
            errorMap.put("msg", "调用第三方登录接口失败");
            session.setAttribute("errors", errorMap);
            return null;
        }

    }


}
