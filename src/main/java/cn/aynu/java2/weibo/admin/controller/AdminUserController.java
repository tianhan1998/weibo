package cn.aynu.java2.weibo.admin.controller;

import cn.aynu.java2.weibo.admin.service.IAdminUserService;
import cn.aynu.java2.weibo.entity.User;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static cn.aynu.java2.weibo.entity.Result.*;
/*
* 管理员对用户进行管理
* 增，删，改，查，权限管理
* */
@RestController
@RequestMapping("/admin/user")
public class AdminUserController {
    @Autowired
    private IAdminUserService iAdminUserService;

    @RequestMapping("/findUserByCondition")
    public JSONObject findUserByCondition(User user){
        JSONObject json=new JSONObject();
        List<User> users = iAdminUserService.findUserByCondition(user);
        if(users.size() > 0){
            json.put("result",successResult("用户查询成功！",users));
        } else{
            json.put("result",failResult("用户查询失败！"));
        }
        return json;
    }

    @RequestMapping("/cancelUser")
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
