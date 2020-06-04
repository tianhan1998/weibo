package cn.aynu.java2.weibo.service;

import cn.aynu.java2.weibo.entity.Photo;
import cn.aynu.java2.weibo.entity.Post;
import cn.aynu.java2.weibo.entity.Video;
import cn.aynu.java2.weibo.exception.UploadFailException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author tianh
 */
public interface PostService {

    /**
     * 根据id找到视频实体类
     * @param id
     * @return 视频实体类
     */
    Video selectVideoById(Integer id);
    /**
     * 根据动态找到视频id
     * @param post 动态
     * @return id
     */
    Integer selectVideoIdByPost(Post post);
    /**
     * 根据id数组找所有的图片实体类
     * @param ids
     * @return 图片实体类
     */
    List<Photo> selectPhotosByIds(List<Integer> ids);
    /**
     * 根据动态找到所有相关的图片
     * @param post 动态
     * @return 图片id list
     */
    List<Integer> selectPhotoIdsByPost(Post post);
    /**
     * 查找所有动态
     * @return Post
     */
    List<Post> selectAllPost();
    /**
     * 插入动态
     * @param post 动态对象
     * @param photos 上传的图群
     * @param video 上传的视频
     * @param request 请求
     * @exception UploadFailException 上传失败
     * @return 数据库操作行数
     */
    Boolean insertPost(Post post, MultipartFile[] photos, MultipartFile video, HttpServletRequest request) throws IOException, UploadFailException;


}
