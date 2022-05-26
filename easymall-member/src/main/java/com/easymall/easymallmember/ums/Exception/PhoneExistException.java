package com.easymall.easymallmember.ums.Exception;

/**
 * @Description:
 * @Name: PhoneExitException
 * @Author peipei
 * @Date 2022/4/21
 */
public class PhoneExistException extends RuntimeException {
    public PhoneExistException() {
        super("手机号已被注册");
    }
}
