package cn.aynu.java2.weibo.controller;

import cn.aynu.java2.weibo.entity.Result;
import cn.aynu.java2.weibo.entity.User;
import cn.aynu.java2.weibo.service.IUserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserHandler {
    @Autowired
    IUserService userService;
    //用户登陆
    @RequestMapping("/login")
    @ResponseBody
    public JSONObject login(User user, HttpSession session, HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
            User login_user=userService.findUserByLogin(user);
            //如果用户名和密码正确
            if(login_user!=null){
                session.setAttribute("login_user",login_user);
                //addCookie(user,request,response);
                jsonObject.put("result", Result.successResult("登陆成功", login_user));
            } else{ //如果用户名和密码不正确
                jsonObject.put("result", Result.failResult("用户名或密码错误", login_user));
            }

        return jsonObject;
    }
    //保存用户名和密码
    private void addCookie(User user, HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie1=new Cookie("CookEmail",user.getEmail());
        cookie1.setMaxAge(60*60*24);
        cookie1.setPath(request.getContextPath()+"/");
        response.addCookie(cookie1);
        Cookie cookie2=new Cookie("CookPassword",user.getPassword());
        cookie2.setMaxAge(60*60*24);
        cookie2.setPath(request.getContextPath()+"/");
        response.addCookie(cookie2);
    }

    //用户注册
    @RequestMapping("/register")
    @ResponseBody
    public JSONObject register(User user) throws MessagingException {
        user.setAvatar("/avater/test/1.jpg");
        JSONObject jsonObject = new JSONObject();
        userService.addUser(user);
        jsonObject.put("result", Result.successResult("注册成功"));
        return jsonObject;
    }
    //邮箱验证
    @RequestMapping("/emailCheck")
    @ResponseBody
    public JSONObject emailCheck(User user){
        JSONObject jsonObject = new JSONObject();
        User target=userService.findUserByLogin(user);
        if(target==null){
            jsonObject.put("result", Result.successResult("邮箱验证通过"));
        }else{
            jsonObject.put("result", Result.failResult("该邮箱已被注册"));
        }
        return jsonObject;
    }
    //管理员登陆
    @RequestMapping("/adminUser")
    @ResponseBody
    public JSONObject adminUser(User user){
        JSONObject jsonObject = new JSONObject();
        User login_user=userService.findUserByLogin(user);
        if(login_user!=null){
            if(login_user.getRole()==0){
                jsonObject.put("result", Result.successResult("登陆成功"));
            }
            else{
                jsonObject.put("result", Result.failResult("用户名或密码错误"));
            }
        }
        return jsonObject;
    }
    @RequestMapping("/index")
    public String index(HttpSession session){
        User login_user= (User) session.getAttribute("login_user");
        if(login_user!=null){
            return "main";
        }else {
            return "login";
        }
    }
}
