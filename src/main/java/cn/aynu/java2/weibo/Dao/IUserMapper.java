package cn.aynu.java2.weibo.Dao;

import cn.aynu.java2.weibo.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserMapper {
    User selectUserByLogin(User user);

    void insertUser(User user);
}
