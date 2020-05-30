package cn.aynu.java2.weibo.controller;

import cn.aynu.java2.weibo.entity.Result;
import cn.aynu.java2.weibo.entity.User;
import cn.aynu.java2.weibo.service.IUserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserHandler {
    @Autowired
    IUserService userService;
    //用户登陆
    @RequestMapping("/login")
    @ResponseBody
    public JSONObject login(User user, HttpSession session){
       User login_user=userService.findUserByLogin(user);
        JSONObject jsonObject = new JSONObject();
        //如果用户名和密码正确
        if(login_user!=null){
            session.setAttribute("login_user",login_user);
            jsonObject.put("result", Result.successResult("登陆成功", login_user));
        } else{ //如果用户名和密码不正确
            jsonObject.put("result", Result.failResult("登陆失败", login_user));
        }
        return jsonObject;
    }

    //用户注册
    @RequestMapping("/register")
    @ResponseBody
    public JSONObject register(User user){
        System.out.println(user);
        //userService.addUser(user);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", Result.successResult("注册成功"));
        return jsonObject;
    }
}
