package cn.aynu.java2.weibo.entity;

import java.io.Serializable;

/**
 * video
 * @author tianh
 */
public class Video implements Serializable {
    /**
     * 视频id
     */
    private String id;

    /**
     * 视频资源地址
     */
    private String url;

    /**
     * 视频名
     */
    private String videoName;

    private static final long serialVersionUID = 1L;

    public Video() {
    }

    public Video(String url, String videoName) {
        this.url = url;
        this.videoName = videoName;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", videoName='" + videoName + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }
}