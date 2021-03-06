package cn.aynu.java2.weibo.entity;

/**
 * @author tianh
 */
public class PostGood {
    private String id;
    private String userId;
    private String postId;

    public PostGood() {
    }

    public PostGood(String userId, String postId) {
        this.userId = userId;
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "PostGood{" +
                "id='" + id + '\'' +
                ", user='" + userId + '\'' +
                ", postId='" + postId + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
