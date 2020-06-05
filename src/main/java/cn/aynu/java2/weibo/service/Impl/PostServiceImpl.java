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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tianh
 */
@Service
public class PostServiceImpl implements PostService {
    /**
     * 路径组
     */
    private final String s = System.getProperty("user.dir");
    private final String photoPath= s+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"static"+File.separator+"photos";
    private final String targetPhotoPath=s+File.separator+"target"+File.separator+"classes"+File.separator+"static"+File.separator+"photos";
    private final String videoPath= s+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"static"+File.separator+"videos";
    private final String targetVideoPath=s+File.separator+"target"+File.separator+"classes"+File.separator+"static"+File.separator+"videos";
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
    public Boolean insertPost(Post post, MultipartFile[] photos, MultipartFile video, HttpServletRequest request) throws UploadFailException {
         if(postMapper.insertPost(post)>0){
             File photoFile;
             File photoTargetFile;
             File videoFile;
             File videoTargetFile;
             /*
             记录所有上传过的图片，上传出错后方便删除
              */
             List<File> log=new ArrayList<>();
             if(photos!=null){
                 for (MultipartFile photo : photos) {
                     String fileName = UUIDUtils.uuid() + "+" + photo.getOriginalFilename();
                      photoFile=new File(photoPath, fileName);
                      photoTargetFile= new File(targetPhotoPath,fileName);
                     try {
                         photo.transferTo(photoFile);
                         FileUtils.copyFile(photoFile,photoTargetFile);
                     } catch (IOException e) {
                         deletePhotos(log);
                         e.printStackTrace();
                         throw new UploadFailException("上传图片出错");
                     }
                     log.add(photoTargetFile);
                     log.add(photoFile);
                     Photo tempPhoto=new Photo("photos/"+fileName,fileName);
                     if(postMapper.insertPhoto(tempPhoto)<=0){
                         deletePhotos(log);
                         throw new UploadFailException("插入图片数据库失败");
                     }
                     if(postMapper.insertPostPhoto(tempPhoto, post)<=0){
                         deletePhotos(log);
                         throw new UploadFailException("添加图片关联数据库失败");
                     }
                 }
             }if(video!=null){
                 String fileName=UUIDUtils.uuid()+"+"+video.getOriginalFilename();
                 videoFile=new File(videoPath,fileName);
                 videoTargetFile=new File(targetVideoPath,fileName);
                 try {
                     video.transferTo(videoFile);
                     FileUtils.copyFile(videoFile,videoTargetFile);
                 }catch (IOException e){
                     if(photos!=null){
                         deletePhotos(log);
                     }
                     e.printStackTrace();
                     throw new UploadFailException("上传视频出错");
                 }
                 Video tempVideo=new Video("videos/"+fileName,fileName);
                 if(postMapper.insertVideo(tempVideo)<=0){
                     videoFile.delete();
                     videoTargetFile.delete();
                     if(photos!=null){
                         deletePhotos(log);
                     }
                     throw new UploadFailException("插入视频数据库出错");
                 }
                 if(postMapper.insertPostVideo(tempVideo,post)<=0){
                     videoFile.delete();
                     videoTargetFile.delete();
                     if(photos!=null){
                         deletePhotos(log);
                     }
                     throw new UploadFailException("插入视频关联数据库失败");
                 }
             }
             return true;
         }else{
             return false;
         }
    }

    @Override
    public List<Post> selectAllPostByUserId(String id) {
        return postMapper.selectAllPostByUserId(id);
    }

    @Override
    public boolean deletePost(Post post) {
        List<Integer> photoIds=selectPhotoIdsByPost(post);
        if(photoIds!=null&&photoIds.size()>0){
            List<Photo> photos=selectPhotosByIds(photoIds);
            for(Photo photo:photos){
                File photoFile=new File(photoPath+File.separator+photo.getPhotoName());
                File targetPhotoFile = new File(targetPhotoPath+File.separator+photo.getPhotoName());
                if(photoFile.exists()){
                    photoFile.delete();
                }
                if(targetPhotoFile.exists()){
                    targetPhotoFile.delete();
                }
                deletePhotoById(photo.getId());
            }
            deletePostPhotoByPostId(post.getId().toString());
        }
        Integer videoId=selectVideoIdByPost(post);
        if(videoId!=null) {
            Video video=selectVideoById(videoId);
            File videoFile=new File(videoPath+File.separator+video.getVideoName());
            File targetVideoFile = new File(targetVideoPath+File.separator+video.getVideoName());
            if(videoFile.exists()){
                videoFile.delete();
            }
            if(targetVideoFile.exists()){
                targetVideoFile.delete();
            }
            deleteVideoById(videoId.toString());
            deletePostVideoByPostId(post.getId().toString());
        }
        return postMapper.deletePost(post.getId().toString())>0;
    }

    @Override
    public Post selectPostById(String id) {
        return postMapper.selectPostById(id);
    }

    @Override
    public int deleteVideoById(String id) {
        return postMapper.deleteVideoById(id);
    }

    @Override
    public int deletePhotoById(String id) {
        return postMapper.deletePhotoById(id);
    }

    @Override
    public int deletePostPhotoByPostId(String id) {
        return postMapper.deletePostPhotoByPostId(id);
    }

    @Override
    public int deletePostVideoByPostId(String id) {
        return postMapper.deletePostVideoByPostId(id);
    }

    private void deletePhotos(List<File> photos){
        for (File photo:photos){
            if(photo.exists()){
                photo.delete();
            }
        }
    }

}
