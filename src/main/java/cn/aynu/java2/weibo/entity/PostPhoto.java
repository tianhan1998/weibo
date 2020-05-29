package cn.aynu.java2.weibo.entity;

public class PostPhoto {
    private String id;
    private String photoId;
    private String postId;

    @Override
    public String toString() {
        return "PostGood{" +
                "id='" + id + '\'' +
                ", photoId='" + photoId + '\'' +
                ", postId='" + postId + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
