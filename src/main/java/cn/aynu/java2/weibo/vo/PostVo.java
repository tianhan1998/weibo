package cn.aynu.java2.weibo.vo;

import cn.aynu.java2.weibo.entity.Common;
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
    private Boolean friend;
    private List<Common> commons;

    @Override
    public String toString() {
        return "PostVo{" +
                "post=" + post +
                ", photos=" + photos +
                ", video=" + video +
                ", good=" + good +
                ", friend=" + friend +
                ", commons=" + commons +
                '}';
    }

    public List<Common> getCommons() {
        return commons;
    }

    public void setCommons(List<Common> commons) {
        this.commons = commons;
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

    public Boolean getFriend() {
        return friend;
    }

    public void setFriend(Boolean friend) {
        this.friend = friend;
    }
}
