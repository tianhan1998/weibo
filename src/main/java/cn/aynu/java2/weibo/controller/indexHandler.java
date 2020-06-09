package cn.aynu.java2.weibo.controller;

import cn.aynu.java2.weibo.entity.User;
import cn.aynu.java2.weibo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class indexHandler {
    @Autowired
    IUserService userService;
    //主页进入
    @RequestMapping("/")
    public String index(HttpSession session, HttpServletRequest request){
        //获取session中的登陆信息
        User login_user= (User) session.getAttribute("login_user");
        if(login_user==null){
            //自动登陆
            login_user=autologin(request);
            if(login_user!=null){
                session.setAttribute("login_user",login_user);
                return "main";
            }
            return "login";
        }else {
            return "main";
        }
    }
    //自动登陆方法
    private User autologin(HttpServletRequest request) {
        String email=null;
        String password=null;
        Cookie[] cookies=request.getCookies();
        for(Cookie cookie:cookies){
            if("CookieEmail".equals(cookie.getName())){
                email=cookie.getValue();
            }
            if("CookiePassword".equals(cookie.getName())){
                password=cookie.getValue();
            }
        }
        User user=new User();
        user.setEmail(email);
        user.setPassword(password);
        return userService.findUserByLogin(user);
    }
}
