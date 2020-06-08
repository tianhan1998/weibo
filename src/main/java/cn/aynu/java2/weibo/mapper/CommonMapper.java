package cn.aynu.java2.weibo.mapper;

import cn.aynu.java2.weibo.entity.Common;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tianh
 */
@Repository
public interface CommonMapper {
    /**
     * 通过动态id来查找所有评论
     * @param id 动态id
     * @return 评论list
     */
    List<Common> selectCommonsByPostId(String id);

    /**
     * 插入评论
     * @param common 评论实体列
     * @return 行数
     */
    int insertCommon(Common common);

    /**
     * 根据id查common
     * @param id commonId
     * @return common实体类
     */
    Common selectCommonById(String id);
}