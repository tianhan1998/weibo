package cn.aynu.java2.weibo.admin.service.Impl;

import cn.aynu.java2.weibo.admin.mapper.IAdminPostMapper;
import cn.aynu.java2.weibo.admin.service.IAdminPostService;
import cn.aynu.java2.weibo.entity.Post;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminPostServiceImpl implements IAdminPostService {
    @Resource
    private IAdminPostMapper iAdminPostMapper;
    @Override
    public List<Post> findPost(Post post, Date minTime, Date maxTime,Integer minGood,Integer maxGood) {
        Map map = new HashMap();
        map.put("post",post);
        map.put("minTime",minTime);
        map.put("maxTime",maxTime);
        map.put("minGood",minGood);
        map.put("maxGood",maxGood);
        return iAdminPostMapper.selectPost(map);
    }
}
