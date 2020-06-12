package cn.aynu.java2.weibo.admin.service.Impl;

import cn.aynu.java2.weibo.admin.mapper.IAdminUserMapper;
import cn.aynu.java2.weibo.admin.service.IAdminUserService;
import cn.aynu.java2.weibo.entity.Post;
import cn.aynu.java2.weibo.entity.User;
import cn.aynu.java2.weibo.mapper.CommonMapper;
import cn.aynu.java2.weibo.mapper.PostMapper;
import cn.aynu.java2.weibo.service.Impl.SubServiceImpl;
import cn.aynu.java2.weibo.service.SubService;
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
    private SubService subService;
    @Resource
    private CommonMapper commonMapper;

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

    /**1.用户所用评论删除
     * 2.用户动态删除（评论，赞，photo，video）
     * 3.用户关注，粉丝删除
     * 4.用户信息删除
     * */
    @Transactional
    @Override
    public int cancelUser(String id) {
        //1
        commonMapper.deleteCommonByUserId(id);
        //2
        List<Post> posts = postMapper.selectAllPostByUserId(id);
        List<String> msg = new ArrayList<>();
        for(Post p:posts){
            commonMapper.deleteCommonByPostId(id);
            //3
            subService.deleteAllSubByUserId(id,msg);
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
