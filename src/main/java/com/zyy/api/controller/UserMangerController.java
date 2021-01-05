package com.zyy.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.zyy.api.dao.UserMapper;
import com.zyy.api.model.User;
import com.zyy.api.model.UserExample;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Log4j2
@RestController
public class UserMangerController {


    @Autowired
    private UserMapper userMapper;

     /**
     *获取用户信息
     */
     @RequestMapping("getUserInfo")
     public User userInfo(HttpServletRequest request, @RequestBody User user){
         log.info("请求参数{}",JSONObject.toJSONString(user));
         boolean checkCookie=verifyCookie(request);
         if(!checkCookie){

             return null;
         }
         UserExample example=new UserExample();
         example.createCriteria().andIdEqualTo(user.getId());
         User userInfo=userMapper.selectByExample(example).get(0);
         return userInfo;
     }


    /**
     *新增用户信息
     */
    @RequestMapping("addUserInfo")
    public int addUserInfo(HttpServletRequest request,@RequestBody User user){
        boolean checkCookie=verifyCookie(request);
        if(!checkCookie){
            return 0;
        }
        int res=userMapper.insert(user);
        return res;
    }
    /**
     *更新用户信息
     */
    @RequestMapping("updateUserInfo")
    public int updateUserInfo(HttpServletRequest request,@RequestBody User user){
        boolean checkCookie=verifyCookie(request);
        if(!checkCookie){
            return 0;
        }
        UserExample example=  new UserExample();
        example.createCriteria().andIdEqualTo(user.getId());
        int res=userMapper.updateByExampleSelective(user,example);
        return res;
    }
    /**
     *删除用户信息
     */
    @RequestMapping("delUserInfo")
    public int delUserInfo(HttpServletRequest request,@RequestBody User user){
        boolean checkCookie=verifyCookie(request);
        if(!checkCookie){
            return 0;
        }
        UserExample example=  new UserExample();
        example.createCriteria().andIdEqualTo(user.getId());
        user.setIsDel("1");
        int res=userMapper.updateByExampleSelective(user,example);
        return res;
    }
    //校验cookie信息
    private boolean verifyCookie(HttpServletRequest request) {
        Cookie[] cookies=request.getCookies();
        if(cookies==null){
            log.info("cookie为空");
            return false;

        }
        for (Cookie cookie:cookies) {
            if("login".equals( cookie.getName()) && "true".equals(cookie.getValue())){
                return true;
            }
        }
        return false;
    }

}
