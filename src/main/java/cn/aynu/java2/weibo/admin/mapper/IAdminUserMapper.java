package cn.aynu.java2.weibo.admin.mapper;

import cn.aynu.java2.weibo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IAdminUserMapper {
    List<User> selectUserByCondition(User user);

    int insertUser(User user);

    int deleteUserById(Integer id);
}
