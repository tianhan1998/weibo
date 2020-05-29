package cn.aynu.java2.weibo.entity;

public class PostVideo {
    private String id;
    private String videoId;
    private String postId;

    @Override
    public String toString() {
        return "PostGood{" +
                "id='" + id + '\'' +
                ", videoId='" + videoId + '\'' +
                ", postId='" + postId + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
