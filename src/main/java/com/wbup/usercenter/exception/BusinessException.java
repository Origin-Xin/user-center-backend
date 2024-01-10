package com.wbup.usercenter.exception;

import com.wbup.usercenter.common.ErrorCode;

/**
 * ClassName: BusinessException
 * Package: com.wbup.usercenter.exception
 * Description:自定义异常类
 *
 * @Author YuanDian
 * @Create 2023/12/31 1:36
 * @Version 1.0
 */
public class BusinessException extends RuntimeException{
    private final int code;

    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String description){
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
