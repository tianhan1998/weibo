package cn.aynu.java2.weibo.admin.service.Impl;

import cn.aynu.java2.weibo.admin.mapper.IAdminUserMapper;
import cn.aynu.java2.weibo.admin.service.IAdminUserService;
import cn.aynu.java2.weibo.entity.Post;
import cn.aynu.java2.weibo.entity.User;
import cn.aynu.java2.weibo.mapper.PostMapper;
import cn.aynu.java2.weibo.service.Impl.SubServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class AdminUserServiceImpl implements IAdminUserService {
    @Resource
    private IAdminUserMapper iAdminUserMapper;
    @Resource
    private PostMapper postMapper;
    @Resource
    private SubServiceImpl subService;

    @Override
    public User findUserByInfo(User user) {
        return iAdminUserMapper.selectUserByInfo(user);
    }

    @Override
    public List<User> findUserByCondition(User user, Date beginTime, Date endTime, Date beginDay, Date endDay) {
        Map map = new HashMap();
        map.put("user",user);
        map.put("beginTime",beginTime);
        map.put("endTime",endTime);
        map.put("beginDay",beginDay);
        map.put("endDay",endDay);
        return iAdminUserMapper.selectUserByCondition(map);
    }

    @Override
    public int roleUpdate(Integer id, Integer role) {
        return iAdminUserMapper.roleUpdate(id,role);
    }

    @Override
    public int addUser(User user) {
        return iAdminUserMapper.insertUser(user);
    }

    @Transactional
    @Override
    public int cancelUser(Integer id) {
        List<Post> posts = postMapper.selectAllPostByUserId(id.toString());
        List<String> msg = new ArrayList<>();
        for(Post p:posts){
            subService.deleteAllSubByUserId(id.toString(),msg);
            postMapper.deletePost(p.getId().toString());
        }
        return iAdminUserMapper.deleteUserById(id);
    }

    @Override
    public int findUserCount() {
        return iAdminUserMapper.selectUserCount();
    }

    @Override
    public int findPostCount() {
        return iAdminUserMapper.selectPostCount();
    }

    @Override
    public List<User> findActiveUser() {
        return iAdminUserMapper.selectActiveUser();
    }

    @Override
    public List<Post> findHotPost() {
        return iAdminUserMapper.selectHotPost();
    }

    @Override
    public void modifyRole(User user) {
        iAdminUserMapper.updateRole(user);
    }
}
