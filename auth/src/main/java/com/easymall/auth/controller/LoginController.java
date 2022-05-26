package com.easymall.auth.controller;

import com.alibaba.fastjson.JSON;
import com.easymall.auth.feign.MemberFeignService;
import com.easymall.auth.feign.ThirdPartFeignService;
import com.easymall.auth.vo.UserLoginVo;
import com.easymall.auth.vo.UserRegisterVo;
import easymall.easymallcommon.constant.AuthServerConstant;
import easymall.easymallcommon.exception.BizCodeEnum;
import easymall.easymallcommon.vo.MemberResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Name: LoginController
 * @Author peipei
 * @Date 2022/4/21
 */
@RestController
public class LoginController {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    ThirdPartFeignService thirdPartFeignService;

    @Autowired
    MemberFeignService memberFeignService;


    @GetMapping("/sms/sendCode")
    public R sendCode(@RequestParam("/phone") String phone) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String key = AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone;
        String value = operations.get(key);
        if (value != null && value.length() > 0) {
            long v = Long.parseLong(value.split("_")[1]);
            if (System.currentTimeMillis() - v > 60000) {
                return R.error(BizCodeEnum.SMS_CODE_EXCEPTION.getCode(), BizCodeEnum.SMS_CODE_EXCEPTION.getMsg());

            }
        }
        redisTemplate.delete(key);
        //获取到6位数字的验证码
        String code = String.valueOf((int) ((Math.random() + 1) * 100000));
        //在redis中进行存储并设置过期时间
        operations.set(key, code + "_" + System.currentTimeMillis(), 10, TimeUnit.MINUTES);
        thirdPartFeignService.sendCode(phone, code);
        return R.ok();
    }

    @PostMapping("/register")
    public R register(@Valid UserRegisterVo registerVo, BindingResult result) {
        if (result.hasErrors()) {
            return R.error(BizCodeEnum.VAILD_EXCEPTION.getCode(), BizCodeEnum.VAILD_EXCEPTION.getMsg());
        }
        String key = AuthServerConstant.SMS_CODE_CACHE_PREFIX + registerVo.getPhone();
        String code = redisTemplate.opsForValue()
                .get(key);
        if (code != null && code.split("_")[0].equals(registerVo.getCode())) {
            redisTemplate.delete(key);
            R r = memberFeignService.register(registerVo);
            if (r.getCode() != 0) {
                return R.error(BizCodeEnum.READ_TIME_OUT_EXCEPTION.getCode(), BizCodeEnum.READ_TIME_OUT_EXCEPTION.getMsg());
            } else {
                return R.ok();
            }
        } else {
            return R.error("验证码错误");
        }
    }

    @PostMapping("/login")
    public R login(UserLoginVo loginVo) {
        R r = memberFeignService.login(loginVo);
        if (r.getCode() == 0) {
            String entityStr = JSON.toJSONString(r.get("memberEntity"));
            MemberResponseVo memberResponseVo = JSON.parseObject(entityStr, MemberResponseVo.class);
        }

    }




}
