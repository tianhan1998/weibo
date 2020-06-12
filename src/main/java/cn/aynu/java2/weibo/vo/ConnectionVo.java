package cn.aynu.java2.weibo.vo;

import cn.aynu.java2.weibo.entity.User;

import java.util.List;

/**
 * @author tianh
 */
public class ConnectionVo {
    private User user;
    private Integer commonFriend;
    private List<User> commonFriends;
    private boolean friend;
    public ConnectionVo() {
    }

    @Override
    public String toString() {
        return "ConnectionVo{" +
                "user=" + user +
                ", commonFriend=" + commonFriend +
                ", commonFriends=" + commonFriends +
                ", friend=" + friend +
                '}';
    }

    public boolean isFriend() {
        return friend;
    }

    public void setFriend(boolean friend) {
        this.friend = friend;
    }

    public List<User> getCommonFriends() {
        return commonFriends;
    }

    public void setCommonFriends(List<User> commonFriends) {
        this.commonFriends = commonFriends;
    }

    public ConnectionVo(User user) {
        this.user = user;
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
