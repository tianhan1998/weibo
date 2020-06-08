package cn.aynu.java2.weibo.vo;

import cn.aynu.java2.weibo.entity.User;

public class UserVo {
    private User user;
    private Long gz;
    private Long fs;


    public UserVo(User user) {
        this.user=user;
    }

    @Override
    public String toString() {
        return "UesrVo{" +
                "user=" + user +
                ", gz=" + gz +
                ", fs=" + fs +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getGz() {
        return gz;
    }

    public void setGz(Long gz) {
        this.gz = gz;
    }

    public Long getFs() {
        return fs;
    }

    public void setFs(Long fs) {
        this.fs = fs;
    }

    public UserVo() {
    }
}
