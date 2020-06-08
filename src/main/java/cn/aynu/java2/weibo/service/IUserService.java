package cn.aynu.java2.weibo.service;

import cn.aynu.java2.weibo.entity.User;

import javax.mail.MessagingException;

public interface IUserService {
    User findUserByLogin(User user);


    void addUser(User user) throws MessagingException;

    User selectUserById(String id);
    void activeUser(User user);

    User findUserByEmail(User user);
}
