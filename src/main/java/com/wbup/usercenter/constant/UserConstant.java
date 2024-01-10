package com.wbup.usercenter.constant;

/**
 * ClassName: userConstant
 * Package: com.wbup.usercenter.constant
 * Description:
 *  用户常量
 * @Author YuanDian
 * @Create 2023/11/17 17:16
 * @Version 1.0
 */
public interface UserConstant {
    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE="userLoginState";

    /**
     * 盐值，混淆密码
     */
    String sALT="wbup";

    //---- 权限

    /**
     * 默认权限
     */
    int DEFAULT_ROLE=0;
    /**
     * 管理员权限
     */
    int ADMIN_ROLE=1;
}
