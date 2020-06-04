package cn.aynu.java2.weibo.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * post
 * @author tianhan
 */
public class Post implements Serializable {
    /**
     * 动态主键id
     */
    private Integer id;

    /**
     * 提交动态日期，当前时间戳，不需要插入数据
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date postTime;

    /**
     * 动态正文
     */
    @NotEmpty(message = "动态不能为空")
    private String text;

    /**
     * 点赞数
     */
    private Integer good;

    /**
     * 用户id
     */
    private User user;

    /**
     * 表示动态状态，0表示所有人可见1表示好友可见
     */
    private Integer stage;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", postTime=").append(postTime);
        sb.append(", text=").append(text);
        sb.append(", good=").append(good);
        sb.append(", user=").append(user);
        sb.append(", stage=").append(stage);
        sb.append("]");
        return sb.toString();
    }
}