package cn.aynu.java2.weibo.service.Impl;

import cn.aynu.java2.weibo.entity.User;
import cn.aynu.java2.weibo.service.SubService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
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

    @Override
    public Boolean deleteAllSubByUserId(String id, List<String> msg) {
        boolean flag = true;
        Set<Object> members = redisTemplate.opsForSet().members("gz:userId:" + id);
        Set<Object> fs = redisTemplate.opsForSet().members("fs:userId:" + id);
        if (members != null) {
            for (Object tempId : members) {
                Set<Object> targetFs = redisTemplate.opsForSet().members("fs:userId" + tempId);
                if (targetFs.contains(Integer.parseInt(id))) {
                    if (redisTemplate.opsForSet().remove("fs:userId" + tempId, id) == null) {
                        msg.add("删除用户id为" + tempId + "的粉丝列表目标" + id + "失败");
                        flag = false;
                    }
                }
            }
            redisTemplate.delete("gz:userId:" + id);
        } else if (fs != null) {
            for (Object tempId : fs) {
                Set<Object> targetGz = redisTemplate.opsForSet().members("gz:userId:" + tempId);
                if (targetGz.contains(Integer.parseInt(id))) {
                    if (redisTemplate.opsForSet().remove("gz:userId:" + tempId, id) == null) {
                        msg.add("删除用户" + tempId + "的关注列表" + id + "失败");
                        flag = false;
                    }
                }
            }
            redisTemplate.delete("fs:userId:" + id);
        } else {
            msg.add("用户不存在");
            flag = false;
        }
        return flag;
    }
}
