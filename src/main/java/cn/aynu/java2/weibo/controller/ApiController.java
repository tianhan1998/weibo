package cn.aynu.java2.weibo.controller;

import cn.aynu.java2.weibo.entity.Result;
import cn.aynu.java2.weibo.entity.User;
import cn.aynu.java2.weibo.service.IUserService;
import cn.aynu.java2.weibo.utils.VoUtils;
import cn.aynu.java2.weibo.vo.ConnectionVo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
    @Resource(name = "resultRedisTemplate")
    RedisTemplate<String,Result<Object>> resultRedisTemplate;

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
    @PostMapping("/updateAvatar")
    public JSONObject updateAvatar(MultipartFile avatar, HttpSession session){
        JSONObject json=new JSONObject();
        try{
            if(avatar!=null){
                User user= (User) session.getAttribute("login_user");
                userService.updateAvatar(avatar,user.getId());
                json.put("result",Result.successResult("更换中......"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", Result.exceptionResult(e.getMessage()));
        }
        return json;
    }

    @GetMapping("/result")
    public JSONObject result(String id){
        JSONObject json=new JSONObject();
        try{
            Result<Object> result = resultRedisTemplate.opsForList().leftPop("result:" + id, 5, TimeUnit.SECONDS);
            if(result==null){
                json.put("result",failResult("请过会刷新查看头像"));
            }else{
                json.put("result",result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }
}
