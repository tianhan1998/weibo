package cn.aynu.java2.weibo.mapper;

import cn.aynu.java2.weibo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserMapper {
    User selectUserByLogin(User user);

    void insertUser(User user);

    User selectUserById(String id);

    void activeUser(User user);

    User selectUserByEmail(User user);
}
