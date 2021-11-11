package com.ppfly.exception;

import com.ppfly.enums.ResultEnum;

/**
 * 自定义异常类
 */
public class CreateException extends RuntimeException {

    public CreateException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
    }
}
