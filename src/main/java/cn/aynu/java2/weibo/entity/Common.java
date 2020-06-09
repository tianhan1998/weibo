package cn.aynu.java2.weibo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * common
 * @author tianh
 */
public class Common implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 评论正文
     */
    @NotEmpty(message = "评论不能为空")
    private String text;

    /**
     * 评论用户id
     */
    private User user;

    /**
     * 动态id
     */
    private Post post;

    /**
     * 发表时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date commonTime;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "Common{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", user=" + user +
                ", post=" + post +
                ", commonTime=" + commonTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Date getCommonTime() {
        return commonTime;
    }

    public void setCommonTime(Date commonTime) {
        this.commonTime = commonTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}