package cn.aynu.java2.weibo.service;

import cn.aynu.java2.weibo.entity.*;
import cn.aynu.java2.weibo.exception.DaoAssociationFailException;
import cn.aynu.java2.weibo.exception.UploadFailException;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author tianh
 */
public interface PostService {


    /**
     * 根据id查common
     * @param id commonId
     * @return common实体类
     */
    Common selectCommonById(String id);
    /**
     * 插入评论
     * @param common 评论实体列
     * @return 行数
     */
    int insertCommon(Common common);
    /**
     * 通过动态id来查找所有评论
     * @param id 动态id
     * @return 评论list
     */
    List<Common> selectCommonsByPostId(String id);

    /**
     * 取消点赞
     * @param id 动态id
     * @param postGood 关联实体类
     * @return 成功失败
     */
    Boolean decrGoodByPostId(String id,PostGood postGood) throws DaoAssociationFailException;
    /**
     * 动态点赞
     * @param id 动态id
     * @param postGood 关联实体类
     * @return 成功失败
     */
    Boolean incrGoodByPostId(String id,PostGood postGood) throws DaoAssociationFailException;

    /**
     * 查找对应用户id是否点赞对应动态
     * @param postId 动态id
     * @param userId 用户id
     * @return 条数
     */
    int selectIsGoodByUserIdAndPostId(@Param("postId") String postId, @Param("userId") String userId);
    /**
     * 根据id找到视频实体类
     * @param id id
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
     * @param ids 图片id组
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
     * @param pageNum 页码数
     */
    PageInfo<Post> selectAllPost(String pageNum);
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

    /**
     * 根据用户id搜索所有动态
     * @param id 用户id
     * @param pageNum 页码
     * @return post实体类list
     */
    PageInfo<Post> selectAllPostByUserId(String id,String pageNum);


    /**
     * 删除动态
     * @param post post对象
     * @return 行数
     */
    boolean deletePost(Post post);

    /**
     * 根据id选择post实体类
     * @param id id
     * @return post实体类
     */
    Post selectPostById(String id);

    /**
     * 根据id删除视频
     * @param id id
     * @return 行数
     */
    int deleteVideoById(String id);

    /**
     * 根据id删除图片
     * @param id id
     * @return 行数
     */
    int deletePhotoById(String id);

    /**
     * 根据id删除图片动态关联表
     * @param id id
     * @return 行数
     */
    int deletePostPhotoByPostId(String id);

    /**
     * 根据id删除视频动态关联表
     * @param id id
     * @return 行数
     */
    int deletePostVideoByPostId(String id);

    /*根据评论id删除评论*/
    int deleteCommonById(String id);

    /*查询根据用户删除所有id的总条数*/
    int selectCommonNumByUserId(String id);

    /*根据用户删除所有id*/
    int deleteCommonByUserId(String id);

    /*查询根据用户删除所有id的总条数*/
    int selectCommonNumByPostId(String id);

    /*根据post_id删除动态评论*/
    int deleteCommonByPostId(String id);
}
