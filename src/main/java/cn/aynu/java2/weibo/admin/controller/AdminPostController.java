package cn.aynu.java2.weibo.admin.controller;

import cn.aynu.java2.weibo.admin.service.IAdminPostService;
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
import java.util.Date;
import java.util.List;

import static cn.aynu.java2.weibo.entity.Result.*;

@Controller
@RequestMapping("/admin/post")
public class AdminPostController {
    @Resource
    private IAdminPostService iAdminPostService;

    @Resource
    private PostService postService;
    // 用户动态查询
    //@RequestMapping("/findAllPost")

    // 动态查询by用户id
    @RequestMapping("/findPost")
    public String findPost(Post post, Date minTime, Date maxTime, Model model,Integer minGood,Integer maxGood){
        try{
            if(post.getUser()==null){
                post.setUser(new User());
            }
            List<Post> posts = iAdminPostService.findPost(post,minTime,maxTime,minGood,maxGood);
            if (posts.size()>0){
                model.addAttribute("maxTime",maxTime);
                model.addAttribute("minTime",minTime);
                model.addAttribute("minGood",minGood);
                model.addAttribute("maxGood",maxGood);
                model.addAttribute("post",post);
                model.addAttribute("posts",posts);
            }else{
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "system/post_list";
    }

    //动态查询by动态id
    @RequestMapping("/findPostById")
    @ResponseBody
    public JSONObject findPostById(String id){
        System.out.println(id);
        JSONObject json = new JSONObject();
        try{
            Post post = postService.selectPostById(id);
            System.out.println(post);
            if(post!=null){
                json.put("result",successResult("查询成功",post));
            }else{
                json.put("result",failResult("查询失败"));
            }
        }catch (Exception e){
            e.printStackTrace();
            json.put("result",exceptionResult(e.getMessage()));
        }
        return json;
    }

    // 用户动态删除
    @RequestMapping("/deletePost")
    @ResponseBody
    public JSONObject deletePost(String id){
        System.out.println(id);
        JSONObject json = new JSONObject();
        try{
            Post targetPost=postService.selectPostById(id);
            if(targetPost!=null){
                if(postService.deletePost(targetPost)){
                    json.put("result",successResult("删除动态成功"));
                }else{
                    json.put("result",failResult("删除动态失败"));
                }
            }else{
                json.put("result",failResult("删除动态失败"));
            }
        }catch (Exception e){
            e.printStackTrace();
            json.put("result",exceptionResult(e.getMessage()));
        }
        return json;
    }
}
