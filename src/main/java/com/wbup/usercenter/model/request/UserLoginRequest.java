package com.wbup.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: userLoginRequest
 * Package: com.wbup.usercenter.model.request
 * Description:
 *
 * @Author YuanDian
 * @Create 2023/11/17 0:43
 * @Version 1.0
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = -6807588057282384085L;
    private String userAccount;
    private String userPassword;
}
