package cn.aynu.java2.weibo.rabbit;

import cn.aynu.java2.weibo.entity.Result;
import cn.aynu.java2.weibo.mapper.IUserMapper;
import cn.aynu.java2.weibo.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author tianh
 */
@Slf4j
@Component
public class AvatarListener {

    private static final String UPDATE_AVATAR_QUEUE ="update_avatar";

    @Resource
    IUserMapper mapper;

    @Resource(name = "resultRedisTemplate")
    RedisTemplate<String, Result<Object>> resultRedisTemplate;

    @RabbitListener(queues = UPDATE_AVATAR_QUEUE)
    public void updateAvatar(Map<String,String>params){
        if(params!=null&&params.size()>0){
            log.info("消息队列接收到消息------------>"+params);
            if(mapper.updateAvatarByUserId(params.get("avatarUrl"),params.get("id"))>0){
                resultRedisTemplate.opsForList().leftPush("result:"+params.get("id"),Result.successResult("更换成功"));
            }else{
                resultRedisTemplate.opsForList().leftPush("result:"+params.get("id"),Result.failResult("更换失败"));
            }
        }
    }
}
