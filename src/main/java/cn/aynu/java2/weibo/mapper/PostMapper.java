package cn.aynu.java2.weibo.mapper;

import cn.aynu.java2.weibo.entity.Photo;
import cn.aynu.java2.weibo.entity.Post;
import cn.aynu.java2.weibo.entity.Video;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tianh
 */
@Repository
public interface PostMapper {


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
    List<Photo> selectPhotosByIds(@Param("ids") List<Integer> ids);
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
     * @return 数据库操作行数
     */
    int insertPost(Post post);

    /**
     * 插入图片动态关联表
     * @param photo 图片
     * @param post 动态
     * @return 行数
     */
    int insertPostPhoto(@Param("photo") Photo photo, @Param("post") Post post);

    /**
     * 插入视频动态关联表
     * @param video 视频
     * @param post 动态
     * @return 行数
     */
    int insertPostVideo(@Param("video") Video video, @Param("post") Post post);

    /**
     * 插入图片
     * @param photo 图片
     * @return 行数
     */
    int insertPhoto(Photo photo);

    /**
     * 插入视频
     * @param video 视频
     * @return 行数
     */
    int insertVideo(Video video);
}