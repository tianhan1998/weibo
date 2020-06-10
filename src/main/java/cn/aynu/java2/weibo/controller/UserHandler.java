package cn.aynu.java2.weibo.controller;

import cn.aynu.java2.weibo.entity.Result;
import cn.aynu.java2.weibo.entity.User;
import cn.aynu.java2.weibo.service.IUserService;
import cn.aynu.java2.weibo.utils.UUIDUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
    @Resource(name = "redisTemplate")
    RedisTemplate<Object,Object> redisTemplate;
    //用户登陆
    @RequestMapping("/login")
    @ResponseBody
    public JSONObject login(User user, HttpSession session,String autologin,String remember, HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        User login_user=userService.findUserByLogin(user);
        System.out.println(user);
        //如果用户名和密码正确
        if(login_user!=null){
            if(login_user.getState()==1){//用户已激活
                if("1".equals(remember)){//保存用户名
                    addCookie(autologin,user,request,response);
                }
                else if("1".equals(autologin)){//保存用户名和密码
                    addCookie(autologin,user,request,response);
                }
                session.setAttribute("login_user",login_user);
                jsonObject.put("result", Result.successResult("登陆成功", login_user));
            }
            else{//用户名密码正确，但未激活
                jsonObject.put("result", Result.failResult("用户未激活，请激活后使用", login_user));
            }
        } else{ //如果用户名和密码不正确
            jsonObject.put("result", Result.failResult("用户名或密码错误", login_user));
        }

        return jsonObject;
    }
    //保存用户名和密码
    private void addCookie(String autologin,User user, HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie1=new Cookie("CookieEmail",user.getEmail());
        cookie1.setMaxAge(60*60*24);
        cookie1.setPath(request.getContextPath()+"/");
        response.addCookie(cookie1);
        if("1".equals(autologin)){
            Cookie cookie2=new Cookie("CookiePassword",user.getPassword());
            cookie2.setMaxAge(60*60*24);
            cookie2.setPath(request.getContextPath()+"/");
            response.addCookie(cookie2);
        }
    }

    //用户注册
    @RequestMapping("/register")
    @ResponseBody
    public JSONObject register(User user) throws MessagingException {
        user.setAvatar("/img/avatar.jpg");
        user.setActiveCode(UUIDUtils.uuid());
        user.setRole(1);
        System.out.println(user);
        JSONObject jsonObject = new JSONObject();
        userService.addUser(user);
        redisTemplate.opsForSet().add("gz:userId:"+user.getId(),Integer.parseInt(user.getId()));
        redisTemplate.opsForSet().add("fs:userId:"+user.getId(),Integer.parseInt(user.getId()));
        jsonObject.put("result", Result.successResult("注册成功"));
        return jsonObject;
    }
    //邮箱验证
    @RequestMapping("/emailCheck")
    @ResponseBody
    public JSONObject emailCheck(User user){
        JSONObject jsonObject = new JSONObject();
        User target=userService.findUserByEmail(user);
        if(target==null){
            jsonObject.put("result", Result.successResult("邮箱验证通过"));
        }else{
            jsonObject.put("result", Result.failResult("该邮箱已被注册"));
        }
        return jsonObject;
    }
    //邮箱激活
    @RequestMapping("/activeUser")
    public String activeUser(User user){
        userService.activeUser(user);
        return "login";
    }
    //登出
    @RequestMapping("/loginout")
    public String loginout(HttpSession session,HttpServletRequest request,HttpServletResponse response){
        session.removeAttribute("login_user");
        Cookie cookie1 =new Cookie("CookieEmail",null);
        cookie1.setMaxAge(0);
        cookie1.setPath(request.getContextPath()+"/");
        response.addCookie(cookie1);
        Cookie cookie2 =new Cookie("CookiePassword",null);
        cookie2.setMaxAge(0);
        cookie2.setPath(request.getContextPath()+"/");
        response.addCookie(cookie2);
        request.setAttribute("login_error","用户退出成功，请重新登陆！");
        return "login";
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


}
