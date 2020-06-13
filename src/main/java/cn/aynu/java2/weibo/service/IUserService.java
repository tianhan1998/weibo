package cn.aynu.java2.weibo.service;

import cn.aynu.java2.weibo.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;

public interface IUserService {
    User findUserByLogin(User user);


    void addUser(User user) throws MessagingException;

    User selectUserById(String id);
    void activeUser(User user);

    User findUserByEmail(User user);

    void updateAvatar(MultipartFile avatar, String id) throws IOException;
}
