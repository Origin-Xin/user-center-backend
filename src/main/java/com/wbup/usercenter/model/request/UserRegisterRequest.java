package com.wbup.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: UserRegisterRequest
 * Package: com.wbup.usercenter.model.request
 * Description:
 *  用户注册请求体
 * @Author YuanDian
 * @Create 2023/11/17 0:21
 * @Version 1.0
 */
@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = -4271317532544568874L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String invitationCode;
}
