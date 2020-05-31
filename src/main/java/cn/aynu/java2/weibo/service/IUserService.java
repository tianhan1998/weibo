package cn.aynu.java2.weibo.service;

import cn.aynu.java2.weibo.entity.User;

import javax.mail.MessagingException;

public interface IUserService {
    User findUserByLogin(User user);

    void addUser(User user) throws MessagingException;

}
