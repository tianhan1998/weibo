package cn.aynu.java2.weibo.admin.service.Impl;

import cn.aynu.java2.weibo.admin.mapper.IAdminUserMapper;
import cn.aynu.java2.weibo.admin.service.IAdminUserService;
import cn.aynu.java2.weibo.entity.Post;
import cn.aynu.java2.weibo.entity.User;
import cn.aynu.java2.weibo.service.PostService;
import cn.aynu.java2.weibo.service.SubService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class AdminUserServiceImpl implements IAdminUserService {
    @Resource
    private IAdminUserMapper iAdminUserMapper;
    @Resource
    private PostService postService;
    @Resource
    private SubService subService;

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
    public int cancelUser(String id) {
        //1用户关注，粉丝删除
        List<String> msg = new ArrayList<>();
        boolean one = subService.deleteAllSubByUserId(id,msg);
        if(!one)return -1;
        //2用户所用评论删除
        int commonRow = postService.deleteCommonByUserId(id);
        int two = postService.selectCommonNumByUserId(id);
        System.out.println("commonRow的值是：---" + commonRow);
        System.out.println("two的值是：---" + two);
        if(two!=commonRow)return -1;
        postService.deleteGoodByUserId(id);
        //3用户动态删除（评论，赞，photo，video）
        PageInfo<Post> posts = postService.selectAllPostByUserId(id,"1");
        for(Post p:posts.getList()){
            postService.deleteCommonByPostId(p.getId().toString());
            postService.deletePost(p);
        }
        //4用户信息删除
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
