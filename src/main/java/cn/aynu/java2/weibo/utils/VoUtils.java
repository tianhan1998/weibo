package cn.aynu.java2.weibo.utils;

import cn.aynu.java2.weibo.entity.Photo;
import cn.aynu.java2.weibo.entity.Post;
import cn.aynu.java2.weibo.entity.Video;
import cn.aynu.java2.weibo.service.PostService;
import cn.aynu.java2.weibo.vo.PostVo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static cn.aynu.java2.weibo.entity.Result.successResult;

/**
 * @author tianh
 */
@Component
public class VoUtils {

    @Resource
    PostService postService;

    public List<PostVo> transferToPostVo(List<Post> postList){
        List<PostVo> postVoList=new ArrayList<>();
        if (postList != null && postList.size() > 0) {
            for (Post tempPost : postList) {
                //TODO 加上好友判断
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
            return postVoList;
        }else{
            return null;
        }
    }
}
