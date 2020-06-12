package cn.aynu.java2.weibo.controller;

import cn.aynu.java2.weibo.entity.Result;
import cn.aynu.java2.weibo.entity.User;
import cn.aynu.java2.weibo.service.IUserService;
import cn.aynu.java2.weibo.utils.VoUtils;
import cn.aynu.java2.weibo.vo.ConnectionVo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static cn.aynu.java2.weibo.entity.Result.*;

/**
 * @author tianh
 */
@RestController
@RequestMapping("/main/api")
public class ApiController {
    @Resource
    VoUtils voUtils;
    @Resource
    IUserService userService;
    @Resource(name = "redisTemplate")
    RedisTemplate<Object,Object> redisTemplate;

    @GetMapping("/gzConnection")
    public JSONObject getGzConnection(HttpSession s){
        JSONObject json=new JSONObject();
        List<ConnectionVo> vos=new ArrayList<>();
        try{
            User user= (User) s.getAttribute("login_user");
            Set<Object> members = redisTemplate.opsForSet().members("gz:userId:" + user.getId());
            if(members.size()>1){
               vos = voUtils.transferToConnectionVo(members, user);
            }
            json.put("result",successResult("查找成功",vos));
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }
    @GetMapping("/fsConnection")
    public JSONObject getFsConnection(HttpSession s){
        JSONObject json=new JSONObject();
        List<ConnectionVo> vos=new ArrayList<>();
        try{
            User user= (User) s.getAttribute("login_user");
            Set<Object> members = redisTemplate.opsForSet().members("fs:userId:" + user.getId());
            if(members.size()>1){
                vos = voUtils.transferToConnectionVo(members, user);
            }
            json.put("result",successResult("查找成功",vos));
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }
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
                json.put("result", successResult("获取登录态成功!",voUtils.transferToUserVo(user)));
            }else{
                json.put("result", failResult("获取登录态失败,请重新登陆"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }
    @GetMapping("/userCondition")
    public JSONObject userCondition(String id){
        JSONObject json=new JSONObject();
        try{
            User user=userService.selectUserById(id);
            if(user!=null){
                user.setPassword(null);
                user.setBirthday(null);
                user.setRegisterDay(null);
                user.setEmail(null);
                json.put("result", successResult("获取信息成功!",voUtils.transferToUserVo(user)));
            }else{
                json.put("result", failResult("获取用户信息失败"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }
}
