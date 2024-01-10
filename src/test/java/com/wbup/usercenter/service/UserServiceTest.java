package com.wbup.usercenter.service;

import com.wbup.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * ClassName: UserServiceTest
 * Package: com.wbup.usercenter.service
 * Description:用户服务测试
 *
 * @Author YuanDian
 * @Create 2023/11/14 21:04
 * @Version 1.0
 */
@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    public void testAddUser(){
        User user = new User();
        user.setUsername("wbup");
        user.setUserAccount("123");
        user.setAvatarUrl("https://image.baidu.com/search/detail?ct=503316480&z=0&tn=baiduimagedetail&ipn=d&word=%E7%94%B5%E9%94%AF%E4%BA%BA%E5%A4%B4%E5%83%8F&step_word=&lid=12289325221587962509&ie=utf-8&in=&cl=2&lm=-1&st=-1&hd=0&latest=0&copyright=0&cs=2920250406,3163412865&os=2652204101,3385958162&simid=2920250406,3163412865&pn=8&rn=1&di=7264239678495129601&ln=1214&fr=&fmq=1699967874973_R&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=1e&objurl=https%3A%2F%2Finews.gtimg.com%2Fnewsapp_bt%2F0%2F14407251365%2F1000&rpstart=0&rpnum=0&adpicid=0&nojc=undefined&dyTabStr=MCwzLDEsMiw2LDQsNSw4LDcsOQ%3D%3D");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("456");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        //Assertions.assertEquals(true,result);
        Assertions.assertTrue(result);
    }

    @Test
    public void userRegister(){
        String userAccount="walx";
        String userPassword="";
        String checkPassword="12346";
        String invitationCode="8888";
        long result = userService.userRegister(userAccount, userPassword, checkPassword,invitationCode);
        Assertions.assertEquals(-1,result);
        userAccount="lx";
        result = userService.userRegister(userAccount, userPassword, checkPassword,invitationCode);
        Assertions.assertEquals(-1,result);
        userAccount="walx";
        userPassword="12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword,invitationCode);
        Assertions.assertEquals(-1,result);
        userAccount="wa lx";
        userPassword="12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword,invitationCode);
        Assertions.assertEquals(-1,result);
        userAccount="walx";
        checkPassword="12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword,invitationCode);
        Assertions.assertEquals(-1,result);
        userAccount="walx";
        result = userService.userRegister(userAccount, userPassword, checkPassword,invitationCode);
        Assertions.assertTrue(result>0);
    }
}