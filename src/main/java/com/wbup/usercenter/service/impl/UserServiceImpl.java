package com.wbup.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbup.usercenter.common.ErrorCode;
import com.wbup.usercenter.exception.BusinessException;
import com.wbup.usercenter.model.domain.User;
import com.wbup.usercenter.service.UserService;
import com.wbup.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.wbup.usercenter.constant.UserConstant.USER_LOGIN_STATE;
import static com.wbup.usercenter.constant.UserConstant.sALT;

/**
* @author Bo
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-11-14 20:58:24
*/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Resource
    private UserMapper userMapper;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword,String invitationCode) {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount,userAccount,checkPassword)){
            throw new BusinessException(ErrorCode.PARAMTER_ERRO,"用户参数为空");
        }
        if (userAccount.length()<4){
            throw new BusinessException(ErrorCode.PARAMTER_ERRO,"账号过短");
        }
        if (userPassword.length()<8 || checkPassword.length()<8){
            throw new BusinessException(ErrorCode.PARAMTER_ERRO,"用户密码过短");
        }
        if (invitationCode.length()<4){
            throw new BusinessException(ErrorCode.PARAMTER_ERRO,"用户邀请码过短");
        }
        // 账户不能包含特殊字符
        String regex = "[a-zA-Z0-9-_\\.]+";
        boolean isMatched = userAccount.matches(regex);
        if (!isMatched){
            throw new BusinessException(ErrorCode.PARAMTER_ERRO,"账号不能包含特殊字符");
        }
        //密码和校验密码相同
        if (!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMTER_ERRO,"密码和校验密码不相同");
        }
        //账户名不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count==1){
            throw new BusinessException(ErrorCode.PARAMTER_ERRO,"账号不能重复");
        }
        //邀请码必须为存在
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("invitationCode",invitationCode);
        count=userMapper.selectCount(queryWrapper);
        if (count==0) {
            throw new BusinessException(ErrorCode.PARAMTER_ERRO,"用户邀请码不存在");
        }
        // 密码加密
        String encryptPassword=DigestUtils.md5DigestAsHex((sALT + userPassword).getBytes());
        // 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setUserRole(0);
        user.setInvitationCode(invitationCode);
        boolean saveResult = this.save(user);
        if (!saveResult){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"插入数据失败");
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount,userAccount)){
            throw new BusinessException(ErrorCode.PARAMTER_ERRO,"用户参数为空");
        }
        if (userAccount.length()<4){
            throw new BusinessException(ErrorCode.PARAMTER_ERRO,"账号过短");
        }
        if (userPassword.length()<8){
            throw new BusinessException(ErrorCode.PARAMTER_ERRO,"用户密码过短");
        }
        // 账户不能包含特殊字符
        String regex = "[a-zA-Z0-9-_\\.]+";
        boolean isMatched = userAccount.matches(regex);
        if (!isMatched){
            throw new BusinessException(ErrorCode.PARAMTER_ERRO,"账号不能包含特殊字符");
        }
        //2. 密码加密
        String encryptPassword=DigestUtils.md5DigestAsHex((sALT + userPassword).getBytes());
        //查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        //用户不存在
        if (user==null){
            log.info("User login error, account or password mismatch");
            throw new BusinessException(ErrorCode.NULL_ERROR,"用户不存在");
        }
        //3.用户脱敏
        User safetyUser = getSafetyUser(user);
        //4.记录用户的登录态
        HttpSession session = request.getSession();
        session.setAttribute(USER_LOGIN_STATE,user);
        return safetyUser;
    }


    /**
     * 用户脱敏
     * @param originUser 脱敏前的用户
     * @return
     */
    @Override
    public User getSafetyUser(User originUser){
        if (originUser==null){
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setInvitationCode(originUser.getInvitationCode());
        return safetyUser;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        //移除用户登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        throw new BusinessException(ErrorCode.SUCCESS);
    }

}




