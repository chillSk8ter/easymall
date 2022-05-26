package com.easymall.easymallmember.ums.Exception;

/**
 * @Description:
 * @Name: UserExitException
 * @Author peipei
 * @Date 2022/4/21
 */

public class UserExistException extends RuntimeException{
    public UserExistException() {
        super("用户名已被注册");
    }
}
