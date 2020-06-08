package cn.aynu.java2.weibo.service.Impl;

import cn.aynu.java2.weibo.entity.User;
import cn.aynu.java2.weibo.mapper.IUserMapper;
import cn.aynu.java2.weibo.service.IUserService;
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
    public User selectUserById(String id) {
        return userDao.selectUserById(id);
    }

    @Override
    public void addUser(User user)  {
        String eamilMsg="欢迎注册微博,"+"<a href='http://localhost:8080/user/activeUser?activeCode="+user.getActiveCode()+"'>点此激活</a>"+"到登陆页面";
        try {
            MailUtil.sendMail(user.getEmail(),eamilMsg);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        userDao.insertUser(user);
    }

    @Override
    public void activeUser(User user) {
        userDao.activeUser(user);
    }

    @Override
    public User findUserByEmail(User user) {
        return userDao.selectUserByEmail(user);
    }

}
