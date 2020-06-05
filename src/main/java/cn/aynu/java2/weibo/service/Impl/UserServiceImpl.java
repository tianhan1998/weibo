package cn.aynu.java2.weibo.service;

import cn.aynu.java2.weibo.entity.User;
import cn.aynu.java2.weibo.mapper.IUserMapper;
import cn.aynu.java2.weibo.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserMapper userDao;
    @Override
    public User findUserByLogin(User user) {
       return userDao.selectUserByLogin(user);
    }

    @Override
    public void addUser(User user)  {
        String eamilMsg="欢迎注册微博,"+"<a href='http://localhost:8080/login'>点此激活</a>"+"到登陆页面";
        try {
            MailUtil.sendMail(user.getEmail(),eamilMsg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        userDao.insertUser(user);
    }

}
