package cn.aynu.java2.weibo.utils;

import cn.aynu.java2.weibo.entity.*;
import cn.aynu.java2.weibo.service.PostService;
import cn.aynu.java2.weibo.vo.PostVo;
import cn.aynu.java2.weibo.vo.UserVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static cn.aynu.java2.weibo.entity.Result.successResult;

/**
 * @author tianh
 */
@Component
public class VoUtils {

    @Resource
    PostService postService;

    @Resource(name = "redisTemplate")
    RedisTemplate<Object,Object> redisTemplate;

    public List<PostVo> transferToPostVo(List<Post> postList, User user){
        List<PostVo> postVoList=new ArrayList<>();
        if (postList != null && postList.size() > 0) {
            for (Post tempPost : postList) {
                Set<Object> members = redisTemplate.opsForSet().members("gz:userId:" + user.getId());
                PostVo tempVo = new PostVo(tempPost);
                if(members.contains(Integer.parseInt(tempPost.getUser().getId()))){
                    tempVo.setFriend(!tempPost.getUser().getId().equals(user.getId()));
                }else{
                    tempVo.setFriend(false);
                    if(tempPost.getStage().equals(1)){
                        continue;
                    }
                }
                tempVo.setGood(postService.selectIsGoodByUserIdAndPostId(tempPost.getId().toString(), user.getId()) != 0);
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
                List<Common> commons=postService.selectCommonsByPostId(tempPost.getId().toString());
                if(commons!=null&&commons.size()>0){
                    tempVo.setCommons(commons);
                }else{
                    tempVo.setCommons(new ArrayList<>());
                }
                postVoList.add(tempVo);
            }
            return postVoList;
        }else{
            return null;
        }
    }

    public UserVo transferToUserVo(User user){
        if(user!=null) {
            UserVo vo = new UserVo(user);
            Long fs = redisTemplate.opsForSet().size("fs:userId:" + user.getId());
            Long gz = redisTemplate.opsForSet().size("gz:userId:" + user.getId());
            vo.setFs(fs-1);
            vo.setGz(gz-1);
            return vo;
        }else{
            return null;
        }
    }
}
