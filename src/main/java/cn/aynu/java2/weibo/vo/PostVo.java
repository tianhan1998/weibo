package cn.aynu.java2.weibo.vo;

import cn.aynu.java2.weibo.entity.Photo;
import cn.aynu.java2.weibo.entity.Post;
import cn.aynu.java2.weibo.entity.Video;

import java.util.List;

/**
 * @author tianh
 */
public class PostVo {
    private Post post;
    private List<Photo> photos;
    private Video video;
    private Boolean good;

    @Override
    public String toString() {
        return "PostVo{" +
                "post=" + post +
                ", photos=" + photos +
                ", video=" + video +
                ", isGood=" + good +
                '}';
    }

    public Boolean getGood() {
        return good;
    }

    public void setGood(Boolean good) {
        this.good = good;
    }

    public PostVo() {
    }

    public PostVo(Post post) {
        this.post = post;
    }

    public PostVo(Post post, List<Photo> photos, Video video) {
        this.post = post;
        this.photos = photos;
        this.video = video;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
