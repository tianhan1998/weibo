package cn.aynu.java2.weibo.vo;

import cn.aynu.java2.weibo.entity.User;

/**
 * @author tianh
 */
public class ConnectionVo {
    private User user;
    private Integer commonFriend;

    @Override
    public String toString() {
        return "ConnectionVo{" +
                "user=" + user +
                ", commonFriend=" + commonFriend +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCommonFriend() {
        return commonFriend;
    }

    public void setCommonFriend(Integer commonFriend) {
        this.commonFriend = commonFriend;
    }
}
