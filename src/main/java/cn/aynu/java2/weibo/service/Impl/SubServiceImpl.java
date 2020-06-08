package cn.aynu.java2.weibo.service.Impl;

import cn.aynu.java2.weibo.entity.User;
import cn.aynu.java2.weibo.service.SubService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class SubServiceImpl implements SubService {
    @Resource(name = "redisTemplate")
    RedisTemplate<Object,Object> redisTemplate;
    @Override
    public Boolean subById(User source, User dest,StringBuilder msg) {
        Set<Object> members = redisTemplate.opsForSet().members("gz:userId:" + source.getId());
        if(members.contains(Integer.parseInt(dest.getId()))){
            msg.append("您已经关注过了");
            return false;
        }else{
            if(redisTemplate.opsForSet().add("gz:userId:"+source.getId(),Integer.parseInt(dest.getId()))!=null){
                if(redisTemplate.opsForSet().add("fs:userId:"+dest.getId(),Integer.parseInt(source.getId()))!=null){
                    msg.append("关注成功");
                    return true;
                }else{
                    msg.append("添加粉丝列表失败");
                    redisTemplate.opsForSet().remove("gz:userId:"+source.getId(),Integer.parseInt(dest.getId()));
                    return false;
                }
            }else{
                msg.append("添加关注失败");
                return false;
            }
        }
    }

    @Override
    public Boolean unSubById(User source, User dest, StringBuilder msg) {
        Set<Object> members = redisTemplate.opsForSet().members("gz:userId:" + source.getId());
        if(!members.contains(Integer.parseInt(dest.getId()))){
            msg.append("您还没有关注他");
            return false;
        }else{
            if(redisTemplate.opsForSet().remove("gz:userId:"+source.getId(),Integer.parseInt(dest.getId()))!=null){
                if(redisTemplate.opsForSet().remove("fs:userId:"+dest.getId(),Integer.parseInt(source.getId()))!=null){
                    msg.append("取消关注成功");
                    return true;
                }else{
                    msg.append("移除粉丝列表失败");
                    redisTemplate.opsForSet().add("gz:userId:"+source.getId(),Integer.parseInt(dest.getId()));
                    return false;
                }
            }else{
                msg.append("取消关注失败");
                return false;
            }
        }
    }
}
