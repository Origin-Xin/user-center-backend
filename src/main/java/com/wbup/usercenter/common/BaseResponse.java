package com.wbup.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: BaseResponse
 * Package: com.wbup.usercenter.common
 * Description:通用返回类
 *
 * @Author YuanDian
 * @Create 2023/12/28 22:11
 * @Version 1.0
 */
@Data
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 856432907538842418L;

    private int code;

    private T data;

    private String message;

    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, T data,String message) {
        this(code,data,message,"");
    }

    public BaseResponse(int code, T data) {
        this(code,data,"","");
    }

    public BaseResponse(ErrorCode errorCode){
        this(errorCode.getCode(),null,errorCode.getMessage(),errorCode.getDescription());
    }

}
