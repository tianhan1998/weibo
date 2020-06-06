package cn.aynu.java2.weibo.controller;

import cn.aynu.java2.weibo.entity.Result;
import cn.aynu.java2.weibo.entity.User;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static cn.aynu.java2.weibo.entity.Result.*;

/**
 * @author tianh
 */
@RestController
@RequestMapping("/main/api")
public class ApiController {
    @GetMapping("/LoginUser")
    public JSONObject getUser(HttpSession s){
        JSONObject json=new JSONObject();
        try{
            User user= (User) s.getAttribute("login_user");
            if(user!=null){
                user.setPassword(null);
                user.setBirthday(null);
                user.setRegisterDay(null);
                user.setEmail(null);
                json.put("result", successResult("获取登录态成功!",user));
            }else{
                json.put("result", failResult("获取登录态失败,请重新登陆"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }
}
