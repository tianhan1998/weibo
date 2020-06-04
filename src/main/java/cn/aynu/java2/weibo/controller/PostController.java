package cn.aynu.java2.weibo.controller;

import cn.aynu.java2.weibo.entity.*;
import cn.aynu.java2.weibo.exception.UploadFailException;
import cn.aynu.java2.weibo.service.PostService;
import cn.aynu.java2.weibo.vo.PostVo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
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

    @GetMapping("/post")
    public JSONObject getPost() {
        JSONObject json = new JSONObject();
        List<PostVo> postVoList = new ArrayList<>();
        try {
            List<Post> postList = postService.selectAllPost();
            if (postList != null && postList.size() > 0) {
                for (Post tempPost : postList) {
                    PostVo tempVo = new PostVo(tempPost);
                    List<Integer> photoIds = postService.selectPhotoIdsByPost(tempPost);
                    if (photoIds != null && photoIds.size() > 0) {
                        List<Photo> photos = postService.selectPhotosByIds(photoIds);
                        tempVo.setPhotos(photos);
                    }
                    Integer videoId = postService.selectVideoIdByPost(tempPost);
                    if (videoId != null) {
                        Video video = postService.selectVideoById(videoId);
                        tempVo.setVideo(video);
                    }
                    postVoList.add(tempVo);
                }
                json.put("result", successResult("查找成功", postVoList));
            } else {
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
}
