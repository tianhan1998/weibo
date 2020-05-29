package cn.aynu.java2.weibo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

public class Post {
    private String id;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date postTime;
    @NotEmpty(message = "动态不能为空")
    private String text;
    private Integer good;
    private User user;

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", postTime=" + postTime +
                ", text='" + text + '\'' +
                ", good=" + good +
                ", user='" + user + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getGood() {
        return good;
    }

    public void setGood(Integer good) {
        this.good = good;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
