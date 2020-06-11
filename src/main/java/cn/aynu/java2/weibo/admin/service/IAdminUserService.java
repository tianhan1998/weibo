package cn.aynu.java2.weibo.admin.service;

import cn.aynu.java2.weibo.entity.Post;
import cn.aynu.java2.weibo.entity.User;

import java.util.Date;
import java.util.List;

public interface IAdminUserService {
    User findUserByInfo(User user);

    List<User> findUserByCondition(User user, Date beginTime, Date endTime, Date beginDay, Date endDay);

    int roleUpdate(Integer id, Integer role);

    int addUser(User user);

    int cancelUser(Integer id);

    int findUserCount();

    int findPostCount();

    List<User> findActiveUser();

    List<Post> findHotPost();

    void modifyRole(User user);
}
