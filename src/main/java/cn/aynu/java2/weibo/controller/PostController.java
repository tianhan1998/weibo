package cn.aynu.java2.weibo.controller;

import cn.aynu.java2.weibo.entity.*;
import cn.aynu.java2.weibo.service.PostService;
import cn.aynu.java2.weibo.utils.VoUtils;
import cn.aynu.java2.weibo.vo.PostVo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static cn.aynu.java2.weibo.entity.Result.*;


/**
 * @author tianh
 */
@RestController
@RequestMapping("/main")
public class PostController {

    @Resource
    PostService postService;

    @Resource
    VoUtils voUtils;

    @PostMapping("/post/{id}")
    public JSONObject findPostByUser(@PathVariable String id){
        JSONObject json=new JSONObject();
        List<PostVo> postVoList;
        try{
            List<Post> postList=postService.selectAllPostByUserId(id);
            if(postList!=null&&postList.size()>0){
                postVoList=voUtils.transferToPostVo(postList);
                json.put("result",successResult("查找成功",postVoList));
            }else{
                json.put("result",failResult("查找失败",null));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }

    @GetMapping("/post")
    public JSONObject getPost() {
        JSONObject json = new JSONObject();
        List<Post> postList;
        List<PostVo> postVoList;
        try {
            postList=postService.selectAllPost();
            if(postList!=null&&postList.size()>0) {
                postVoList=voUtils.transferToPostVo(postList);
                json.put("result",successResult("查找成功!",postVoList));
            }else{
                json.put("result", failResult("查找失败", null));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }
    @PostMapping("/post")
    public JSONObject postMyPost(HttpServletRequest request, @Valid Post post, BindingResult result, MultipartFile[] photos, MultipartFile video, String onlyFriend) {
        JSONObject json = new JSONObject();
        try {
            if (result.hasErrors()) {
                json.put("result", exceptionResult(result.getAllErrors()));
            }else {
                if (onlyFriend == null) {
                    post.setStage(0);
                } else {
                    post.setStage(1);
                }
                User loginUser = (User) request.getSession().getAttribute("login_user");
                post.setUser(loginUser);
                if (postService.insertPost(post,photos,video,request)) {
                    json.put("result", successResult("插入动态成功"));
                } else {
                    json.put("result", failResult("插入动态失败"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }
    @DeleteMapping("/post")
    public JSONObject deleteMyPost(String id,HttpSession session){
        JSONObject json=new JSONObject();
        try{
            Post targetPost=postService.selectPostById(id);
            if(targetPost!=null){
                User loginUser = (User) session.getAttribute("login_user");
                if(targetPost.getUser().getId().equals(loginUser.getId())){
                    if(postService.deletePost(targetPost)){
                        json.put("result",successResult("删除动态成功!"));
                    }else{
                        json.put("result",failResult("删除动态失败"));
                    }
                }else{
                    json.put("result",failResult("权限错误"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }
}
