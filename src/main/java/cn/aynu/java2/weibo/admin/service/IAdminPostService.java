package cn.aynu.java2.weibo.admin.service;

import cn.aynu.java2.weibo.entity.Post;

import java.util.Date;
import java.util.List;

public interface IAdminPostService {
    List<Post> findPost(Post post, Date minTime, Date maxTime,Integer minGood,Integer maxGood);
}
