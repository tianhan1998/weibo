package cn.aynu.java2.weibo.controller;

import cn.aynu.java2.weibo.entity.*;
import cn.aynu.java2.weibo.service.IUserService;
import cn.aynu.java2.weibo.service.PostService;
import cn.aynu.java2.weibo.service.SubService;
import cn.aynu.java2.weibo.utils.VoUtils;
import cn.aynu.java2.weibo.vo.PostVo;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static cn.aynu.java2.weibo.entity.Result.*;


/**
 * @author tianh
 */
@RestController
@RequestMapping("/main")
public class MainController {

    @Resource
    PostService postService;

    @Resource
    IUserService userService;

    @Resource
    SubService subService;

    @Resource
    VoUtils voUtils;

    @DeleteMapping("/good/{id}")
    public JSONObject cancelGood(@PathVariable String id,HttpSession session){
        JSONObject json=new JSONObject();
        try{
            User loginUser = (User) session.getAttribute("login_user");
            Post post=postService.selectPostById(id);
            if(post!=null){
                if(postService.selectIsGoodByUserIdAndPostId(post.getId().toString(),loginUser.getId())!=0){
                    if(postService.decrGoodByPostId(id,new PostGood(loginUser.getId(),post.getId().toString()))){
                        json.put("result",successResult("取消赞成功"));
                    }else{
                        json.put("result",failResult("取消赞错误"));
                    }
                }else{
                    json.put("result",failResult("您还未点赞"));
                }
            }else{
                json.put("result",failResult("未找到相关动态"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }
    @PutMapping("/good/{id}")
    public JSONObject good(@PathVariable String id,HttpSession session){
        JSONObject json=new JSONObject();
        try{
            User loginUser = (User) session.getAttribute("login_user");
            Post post=postService.selectPostById(id);
            if(post!=null){
                if(postService.selectIsGoodByUserIdAndPostId(post.getId().toString(),loginUser.getId())==0){
                    if(postService.incrGoodByPostId(post.getId().toString(),new PostGood(loginUser.getId(),post.getId().toString()))){
                        json.put("result",successResult("点赞成功"));
                    }else{
                        json.put("result",failResult("点赞错误"));
                    }
                }else{
                    json.put("result",failResult("您已点赞过"));
                }
            }else{
                json.put("result",failResult("未找到相关动态"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }

    @GetMapping("/post/user")
    public JSONObject findPostByUser(String id,@RequestParam(defaultValue = "1") String pageNum,HttpSession session){
        JSONObject json=new JSONObject();
        List<PostVo> postVoList;
        try{
            PageInfo<Post> postPageInfo=postService.selectAllPostByUserId(id,pageNum);
            if(postPageInfo.getList()!=null&&postPageInfo.getList().size()>0){
                User loginUser = (User) session.getAttribute("login_user");
                postVoList=voUtils.transferToPostVo(postPageInfo.getList(),loginUser);
                Result<Object> result=successResult("查找成功",postVoList);
                result.setPageInfo(postPageInfo);
                json.put("result",result);
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
    public JSONObject getPost(HttpSession session,@RequestParam(defaultValue = "1") String pageNum) {
        System.out.println(pageNum);
        JSONObject json = new JSONObject();
        PageInfo<Post> postPageInfo;
        List<PostVo> postVoList;
        try {
            postPageInfo=postService.selectAllPost(pageNum);
            if(postPageInfo.getList()!=null&&postPageInfo.getList().size()>0) {
                User loginUser = (User) session.getAttribute("login_user");
                postVoList=voUtils.transferToPostVo(postPageInfo.getList(),loginUser);
                Result<Object> result=successResult("查找成功!",postVoList);
                result.setPageInfo(postPageInfo);
                json.put("result",result);
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
    @GetMapping("/sub/{id}")
    public JSONObject subscribe(@PathVariable String id,HttpSession s){
        JSONObject json=new JSONObject();
        try{
            User user= (User) s.getAttribute("login_user");
            User targetUser=userService.selectUserById(id);
            if(targetUser!=null){
                if(!(targetUser.getId().equals(user.getId()))){
                    StringBuilder msg= new StringBuilder();
                    if(subService.subById(user,targetUser,msg)) {
                        json.put("result", successResult(msg.toString()));
                    }else{
                        json.put("result",failResult(msg.toString()));
                    }
                }else{
                    json.put("result",failResult("你不能关注自己"));
                }
            }else{
                json.put("result",failResult("未找到用户"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }
    @DeleteMapping("/sub/{id}")
    public JSONObject unSubscribe(@PathVariable String id,HttpSession s){
        JSONObject json=new JSONObject();
        try{
            User user= (User) s.getAttribute("login_user");
            User targetUser=userService.selectUserById(id);
            if(targetUser!=null){
                if(!(targetUser.getId().equals(user.getId()))){
                    StringBuilder msg= new StringBuilder();
                    if(subService.unSubById(user,targetUser,msg)) {
                        json.put("result", successResult(msg.toString()));
                    }else{
                        json.put("result",failResult(msg.toString()));
                    }
                }else{
                    json.put("result",failResult("你不能取消关注自己"));
                }
            }else{
                json.put("result",failResult("未找到用户"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }
    @PostMapping("common")
    public JSONObject postCommon(@RequestBody @Valid Common common,BindingResult bindingResult,HttpSession session){
        JSONObject json=new JSONObject();
        User user= (User) session.getAttribute("login_user");
        try{
            if(bindingResult.hasErrors()){
                json.put("result",exceptionResult(bindingResult.getAllErrors()));
            }else {
                common.setUser(user);
                if(postService.insertCommon(common)>0){
                    common=postService.selectCommonById(common.getId().toString());
                    json.put("result",successResult("评论成功",common));
                }else{
                    json.put("result",failResult("评论失败"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", exceptionResult(e.getMessage()));
        }
        return json;
    }
}
