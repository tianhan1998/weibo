package cn.aynu.java2.weibo.admin.controller;

import cn.aynu.java2.weibo.admin.service.IAdminPostService;
import cn.aynu.java2.weibo.entity.*;
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
    public String findPostById(String id,Model model){
        System.out.println(id);
        Post post = postService.selectPostById(id);
        if(post!=null) {
            System.out.println(post);
            model.addAttribute("post", post);
            //图片
            List<Integer> pid = postService.selectPhotoIdsByPost(post);
            for(Integer p:pid){
                System.out.println("p的值是：---" + p + ",当前方法=AdminPostController.findPostById()");
            }
            if(pid.size()>0){
                System.out.println("---");
                List<Photo> photos = postService.selectPhotosByIds(pid);
                model.addAttribute("photos",photos);
            }
            //视频
            Integer vid = postService.selectVideoIdByPost(post);
            System.out.println("vid的值是：---" + vid + ",当前方法=AdminPostController.findPostById()");
            if(vid!=null){
                System.out.println("---");
                Video video = postService.selectVideoById(vid);
                model.addAttribute("video",video);
            }
            //评论
            List<Common> commons = postService.selectCommonsByPostId(post.getId().toString());
            model.addAttribute("commons",commons);
        }
        return "system/post_info";
    }

    // 用户动态删除
    @RequestMapping("/deletePost")
    @ResponseBody
    public JSONObject deletePost(String id){
        System.out.println(id);
        JSONObject json = new JSONObject();
        try{
            postService.deleteCommonByPostId(id);
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
