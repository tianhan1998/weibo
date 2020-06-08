package cn.aynu.java2.weibo.admin.controller;

import cn.aynu.java2.weibo.admin.service.IAdminUserService;
import cn.aynu.java2.weibo.entity.User;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

import static cn.aynu.java2.weibo.entity.Result.*;
/*
* 管理员对用户进行管理
* 增，删，改，查，权限管理
* */
@Controller
@RequestMapping("/admin/user")
public class AdminUserController {
    @Autowired
    private IAdminUserService iAdminUserService;

    @RequestMapping("/adminLogin")
    @ResponseBody
    public JSONObject adminLogin(User user, Model model){
        JSONObject json = new JSONObject();
        User user_Admin = iAdminUserService.findUserByInfo(user);
        if(user_Admin != null){
            if(user_Admin.getRole() == 0){
                model.addAttribute("user",user_Admin);
                json.put("result",successResult("管理员登录成功！",user_Admin));
            }else {
                json.put("result",failResult("抱歉，当前用户权限不足！"));
            }
        } else {
            json.put("result",failResult("邮箱或密码错误！"));
        }
        return json;
    }

    @RequestMapping("/findUserByCondition")
    public String findUserByCondition(User user, Date beginTime,Date endTime,Date beginDay,Date endDay,Model model){
        JSONObject json=new JSONObject();
        List<User> users = iAdminUserService.findUserByCondition(user,beginTime,endTime,beginDay,endDay);
        if(users.size() > 0){
            model.addAttribute("user",user);
            model.addAttribute("users",users);
            json.put("result",successResult("信息查询成功！",users));
        } else{
            json.put("result",failResult("信息查询失败！"));
        }
        return "system/mem_list.html";
    }

    @RequestMapping("/cancelUser")
    @ResponseBody
    public JSONObject cancelUser(Integer id){
        System.out.println(id);
        JSONObject json=new JSONObject();
        int row = iAdminUserService.cancelUser(id);
        if(row > 0){
            json.put("result",successResult(id+" 该id账户已注销！"));
        } else{
            json.put("result",failResult(id+"该账户注销失败！"));
        }
        return json;
    }

    @RequestMapping("/addUser")
    @ResponseBody
    public JSONObject addUser(User user){
        JSONObject json=new JSONObject();
        user.setAvatar("/avatar/test/1.jpg");
        int row = iAdminUserService.addUser(user);
        if(row > 0){
            json.put("result",successResult(" 用户添加成功！"));
        } else{
            json.put("result",failResult("添加用户失败！"));
        }
        return json;
    }
}
