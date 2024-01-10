package com.wbup.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wbup.usercenter.model.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Bo
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2023-11-14 20:58:24
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @param invitationCode 邀请码
     * @return 返回新用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword,String invitationCode);


    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 返回脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originUser 脱敏前的用户
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);
}
