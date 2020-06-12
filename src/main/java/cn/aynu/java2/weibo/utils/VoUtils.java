package cn.aynu.java2.weibo.utils;

import cn.aynu.java2.weibo.entity.*;
import cn.aynu.java2.weibo.service.IUserService;
import cn.aynu.java2.weibo.service.PostService;
import cn.aynu.java2.weibo.vo.ConnectionVo;
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

    @Resource
    IUserService userService;

    @Resource(name = "redisTemplate")
    RedisTemplate<Object,Object> redisTemplate;

    public List<ConnectionVo> transferToConnectionVo(Set<Object> userList,User me) {
        if (userList != null) {
            List<ConnectionVo> results = new ArrayList<>();
            for (Object userId : userList) {
                List<User> commonUsers = new ArrayList<>();
                if(userId.equals(Integer.parseInt(me.getId()))){
                    continue;
                }
                User tempUser=userService.selectUserById(userId.toString());
                ConnectionVo connectionVo = new ConnectionVo(tempUser);
                Set<Object> intersect = redisTemplate.opsForSet().intersect("gz:userId:" + me.getId(), "gz:userId:" + tempUser.getId());
                connectionVo.setCommonFriend(intersect.size());
                for(Object commonId:intersect){
                    User tempCommon=userService.selectUserById(commonId.toString());
                    /*
                    自关注id，不算
                     */
                    if(tempCommon.getId().equals(me.getId())){
                        connectionVo.setCommonFriend(connectionVo.getCommonFriend()-1);
                        continue;
                    }
                    /*
                    互相关注，不算
                     */
                    if(commonId.equals(userId)){
                        connectionVo.setCommonFriend(connectionVo.getCommonFriend()-1);
                        continue;
                    }
                    commonUsers.add(tempCommon);
                }
                Set<Object> myGz=redisTemplate.opsForSet().members("gz:userId:"+me.getId());
                if(myGz.contains(userId)){
                    connectionVo.setFriend(true);
                }
                connectionVo.setCommonFriends(commonUsers);
                results.add(connectionVo);
            }
            return results;
        }
        return null;
    }

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
