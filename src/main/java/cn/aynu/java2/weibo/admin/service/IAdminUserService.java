package cn.aynu.java2.weibo.admin.service;

import cn.aynu.java2.weibo.entity.User;

import java.util.List;

public interface IAdminUserService {
    List<User> findUserByCondition(User user);

    int addUser(User user);

    int cancelUser(Integer id);
}
