package cn.aynu.java2.weibo.service;

import cn.aynu.java2.weibo.entity.User;

import java.util.List;

public interface SubService {
    /**
     * 加关注
     * @param source 原用户
     * @param dest 关注用户
     * @param msg 信息
     * @return 成功？
     */
    Boolean subById(User source,User dest,StringBuilder msg);
    /**
     * 取消关注
     * @param source 原用户
     * @param dest 关注用户
     * @param msg 信息
     * @return 成功？
     */
    Boolean unSubById(User source,User dest,StringBuilder msg);

    /**
     * 根据用户id删除所有关联的关注和粉丝
     * @param id 用户id
     * @param msg 返回的信息
     * @return 成功是否
     */
    Boolean deleteAllSubByUserId(String id, List<String> msg);
}
