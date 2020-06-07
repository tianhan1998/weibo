package cn.aynu.java2.weibo.service;

import cn.aynu.java2.weibo.entity.User;

public interface IUserService {
    User findUserByLogin(User user);

    void addUser(User user);

    User selectUserById(String id);

}
