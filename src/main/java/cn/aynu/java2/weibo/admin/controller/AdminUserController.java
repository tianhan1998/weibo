package cn.aynu.java2.weibo.admin.controller;

import cn.aynu.java2.weibo.admin.service.IAdminUserService;
import cn.aynu.java2.weibo.entity.Post;
import cn.aynu.java2.weibo.entity.User;
import cn.aynu.java2.weibo.service.PostService;
import cn.aynu.java2.weibo.service.SubService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
    @Resource
    private IAdminUserService iAdminUserService;
    @Resource
    private PostService postService;
    @Resource
    private SubService subService;

    //管理员登录
    @RequestMapping("/adminLogin")
    @ResponseBody
    public JSONObject adminLogin(User user, HttpSession session){
        JSONObject json = new JSONObject();
        User user_Admin = iAdminUserService.findUserByInfo(user);
        if(user_Admin != null){
            if(user_Admin.getRole() == 0){
                session.setAttribute("login_user",user);
                json.put("result",successResult("管理员登录成功！",user_Admin));
            }else {
                json.put("result",failResult("抱歉，当前用户权限不足！"));
            }
        } else {
            json.put("result",failResult("邮箱或密码错误！"));
        }
        return json;
    }

    //用户信息复合条件查询
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
        return "system/mem_list";
    }

    //用户权限修改
    @RequestMapping("/roleUpdate")
    @ResponseBody
    public JSONObject roleUpdate(Integer id,Integer role){
        JSONObject json=new JSONObject();
        try {
            int row = iAdminUserService.roleUpdate(id,role);
            if(row > 0){
                json.put("result",successResult("权限修改成功！"));
            }else {
                json.put("result",failResult("权限修改失败！"));
            }
        } catch (Exception e){
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }

    //用户注销-管理端
    @RequestMapping("/cancelUser")
    @ResponseBody
    public JSONObject cancelUser(String id){
        JSONObject json=new JSONObject();
        try{
            int row = iAdminUserService.cancelUser(id);
            if(row > 0){
                json.put("result",successResult(id+" 该id账户已注销！"));
            } else{
                json.put("result",failResult(id+"该账户注销失败！"));
            }
        }catch (Exception e){
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }

    //用户退出
    @RequestMapping("/exit")
    @ResponseBody
    public JSONObject exit(HttpSession session){
        JSONObject json = new JSONObject();
        session.removeAttribute("");
        json.put("result",successResult("login_user"));
        return json;
    }

    @RequestMapping("/addAdmin")
    @ResponseBody
    public JSONObject addUser(User user){
        System.out.println(user);
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

     //管理端首页信息
    @RequestMapping("/statistics")
    public String statistics(Model model){
        //用户总数
        int userCount = iAdminUserService.findUserCount();
        //本周动态数
        int postCount = iAdminUserService.findPostCount();
        //活跃用户
        //List<User> users = iAdminUserService.findActiveUser();
        //热门动态
        List<Post> posts = iAdminUserService.findHotPost();
        model.addAttribute("userCount",userCount);
        model.addAttribute("postCount",postCount);
        //model.addAttribute("users",users);
        model.addAttribute("posts",posts);
        return "system/main";
    }
    //改变用户权限
    @RequestMapping("/modifyRole")
    @ResponseBody
    public JSONObject modifyRole(User user){
        JSONObject json = new JSONObject();
        System.out.println("前台接收到的user="+user);
        iAdminUserService.modifyRole(user);
        json.put("result",successResult("权限修改成功！",user));
        return json;
    }
}
