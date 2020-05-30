package cn.aynu.java2.weibo.admin.service.Impl;

import cn.aynu.java2.weibo.admin.mapper.IAdminUserMapper;
import cn.aynu.java2.weibo.admin.service.IAdminUserService;
import cn.aynu.java2.weibo.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminUserServiceImpl implements IAdminUserService {
    @Resource
    private IAdminUserMapper iAdminUserMapper;

    @Override
    public List<User> findUserByCondition(User user) {
        return iAdminUserMapper.selectUserByCondition(user);
    }

    @Override
    public int addUser(User user) {
        System.out.println(11);
        return iAdminUserMapper.insertUser(user);
    }

    @Override
    public int cancelUser(Integer id) {
        return iAdminUserMapper.deleteUserById(id);
    }
}
