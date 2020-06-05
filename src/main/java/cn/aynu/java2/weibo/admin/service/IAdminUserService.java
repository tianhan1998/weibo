package cn.aynu.java2.weibo.admin.service;

import cn.aynu.java2.weibo.entity.User;

import java.util.Date;
import java.util.List;

public interface IAdminUserService {
    User findUserByInfo(User user);

    List<User> findUserByCondition(User user, Date beginTime, Date endTime, Date beginDay, Date endDay);

    int addUser(User user);

    int cancelUser(Integer id);
}
