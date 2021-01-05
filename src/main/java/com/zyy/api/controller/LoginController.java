package com.zyy.api.controller;

import com.zyy.api.dao.UserMapper;
import com.zyy.api.model.User;
import com.zyy.api.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.CookieStore;
import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    /**
     *登录，返回cookie
     */
    @RequestMapping("login")
    public boolean login(HttpServletResponse response,@RequestBody User user){
        UserExample example=new UserExample();
        example.createCriteria().andUserNameEqualTo(user.getUserName()).andPasswordEqualTo(user.getPassword());
        List<User> users=userMapper.selectByExample(example);
        if(users!=null && users.size()>0){
            User userInfo=users.get(0);
            if(userInfo!=null){
                //登录成功
                Cookie cookie = new Cookie("login","true");
                response.addCookie(cookie);
                return true;
            }
        }
        return false;
    }
}
