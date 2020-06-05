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

    @Override
    public String toString() {
        return "PostVo{" +
                "post=" + post +
                ", photos=" + photos +
                ", video=" + video +
                '}';
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
