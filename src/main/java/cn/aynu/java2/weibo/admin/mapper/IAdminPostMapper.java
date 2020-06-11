package cn.aynu.java2.weibo.admin.mapper;

import cn.aynu.java2.weibo.entity.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IAdminPostMapper {
    List<Post> selectPost(Map map);
}
