package cn.aynu.java2.weibo.service.Impl;

import cn.aynu.java2.weibo.entity.Photo;
import cn.aynu.java2.weibo.entity.Post;
import cn.aynu.java2.weibo.entity.Video;
import cn.aynu.java2.weibo.exception.UploadFailException;
import cn.aynu.java2.weibo.mapper.PostMapper;
import cn.aynu.java2.weibo.service.PostService;
import cn.aynu.java2.weibo.utils.UUIDUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @author tianh
 */
@Service
public class PostServiceImpl implements PostService {
    @Resource
    private PostMapper postMapper;

    @Override
    public Video selectVideoById(Integer id) {
        return postMapper.selectVideoById(id);
    }

    @Override
    public Integer selectVideoIdByPost(Post post) {
        return postMapper.selectVideoIdByPost(post);
    }

    @Override
    public List<Photo> selectPhotosByIds(List<Integer> ids) {
        return postMapper.selectPhotosByIds(ids);
    }

    @Override
    public List<Integer> selectPhotoIdsByPost(Post post) {
        return postMapper.selectPhotoIdsByPost(post);
    }

    @Override
    public List<Post> selectAllPost() {
        return postMapper.selectAllPost();
    }

    @Override
    @Transactional(rollbackFor = UploadFailException.class)
    public Boolean insertPost(Post post, MultipartFile[] photos, MultipartFile video, HttpServletRequest request) throws UploadFailException, FileNotFoundException {
         if(postMapper.insertPost(post)>0){
             String s = System.getProperty("user.dir");
             if(photos!=null){
                 String photoPath= s+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"static"+File.separator+"photos";
                 String targetPhotoPath=s+File.separator+"target"+File.separator+"classes"+File.separator+"static"+File.separator+"photos";
                 for (MultipartFile photo : photos) {
                     String fileName = UUIDUtils.uuid() + "+" + photo.getOriginalFilename();
                     File file = new File(photoPath, fileName);
                     try {
                         photo.transferTo(file);
                         FileUtils.copyFile(file,new File(targetPhotoPath,fileName));
                     } catch (IOException e) {
                         e.printStackTrace();
                         throw new UploadFailException("上传图片出错");
                     }
                     Photo tempPhoto=new Photo("photos/"+fileName,fileName);
                     if(postMapper.insertPhoto(tempPhoto)<=0){
                         throw new UploadFailException("插入图片数据库失败");
                     }
                     if(postMapper.insertPostPhoto(tempPhoto, post)<=0){
                         throw new UploadFailException("添加图片关联数据库失败");
                     }
                 }
             }if(video!=null){
                 String videoPath= s+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"static"+File.separator+"videos";
                 String targetVideoPath=s+File.separator+"target"+File.separator+"classes"+File.separator+"static"+File.separator+"videos";
                 String fileName=UUIDUtils.uuid()+"+"+video.getOriginalFilename();
                 File file=new File(videoPath,fileName);
                 try {
                     video.transferTo(file);
                     FileUtils.copyFile(file,new File(targetVideoPath,fileName));
                 }catch (IOException e){
                     e.printStackTrace();
                     throw new UploadFailException("上传视频出错");
                 }
                 Video tempVideo=new Video("videos/"+fileName,fileName);
                 if(postMapper.insertVideo(tempVideo)<=0){
                     throw new UploadFailException("插入视频数据库出错");
                 }
                 if(postMapper.insertPostVideo(tempVideo,post)<=0){
                     throw new UploadFailException("插入视频关联数据库失败");
                 }
             }
             return true;
         }else{
             return false;
         }
    }

}
