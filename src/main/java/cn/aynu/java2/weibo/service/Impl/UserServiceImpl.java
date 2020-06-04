package cn.aynu.java2.weibo.service.Impl;

import cn.aynu.java2.weibo.entity.User;
import cn.aynu.java2.weibo.mapper.IUserMapper;
import cn.aynu.java2.weibo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    IUserMapper userDao;
    @Override
    public User findUserByLogin(User user) {
       return userDao.selectUserByLogin(user);
    }

    @Override
    public void addUser(User user) {
        userDao.insertUser(user);
    }
}
