package cn.aynu.java2.weibo.service;

import cn.aynu.java2.weibo.entity.User;

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
}
