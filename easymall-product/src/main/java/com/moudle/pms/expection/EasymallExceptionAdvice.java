package com.moudle.pms.expection;

import easymall.easymallcommon.exception.BizCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * @Description:
 * @Name: easymallExpectionAdvice
 * @Author peipei
 * @Date 2022/3/21
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.moudle.pms")
public class EasymallExceptionAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("数据校验出现问题，异常类型：{}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        HashMap<String, String> errorMap = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return R.error(BizCodeEnum.VAILD_EXCEPTION.getCode(),BizCodeEnum.VAILD_EXCEPTION.getMsg()).put("data", errorMap);
    }


}
