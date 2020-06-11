package cn.aynu.java2.weibo.admin.mapper;

import cn.aynu.java2.weibo.entity.Post;
import cn.aynu.java2.weibo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

public interface IAdminUserMapper {

    User selectUserByInfo(User user);

    List<User> selectUserByCondition(Map map);

    int roleUpdate(Integer id, Integer role);

    int insertUser(User user);

    int deleteUserById(Integer id);

    int selectUserCount();

    int selectPostCount();

    List<User> selectActiveUser();

    List<Post> selectHotPost();

    void updateRole(User user);
}
