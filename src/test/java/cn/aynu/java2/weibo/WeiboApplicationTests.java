package cn.aynu.java2.weibo;

import cn.aynu.java2.weibo.admin.mapper.IAdminUserMapper;
import cn.aynu.java2.weibo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class WeiboApplicationTests {
    @Resource(name = "redisTemplate")
    RedisTemplate<Object,Object> redisTemplate;

    @Resource
    IAdminUserMapper adminUserMapper;

    @Resource
    RabbitAdmin admin;

    @Test
    void contextLoads() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("user",new User());
//        map.put("beginTime",null);
//        map.put("endTime",null);
//        map.put("beginDay",null);
//        map.put("endDay",null);
//        List<User> users=adminUserMapper.selectUserByCondition(map);
//        for(User tempUser :users){
//            redisTemplate.opsForSet().add("gz:userId:"+tempUser.getId(),Integer.parseInt(tempUser.getId()));
//            redisTemplate.opsForSet().add("fs:userId:"+tempUser.getId(),Integer.parseInt(tempUser.getId()));
//        }
    }
    @Test
    void setRabbit(){
//        TopicExchange weibo = new TopicExchange("weibo_avatar", true, false);
//        admin.declareExchange(weibo);
//        Queue update_queues = new Queue("update_avatar", true);
//        admin.declareQueue(update_queues);
//        Binding binding=BindingBuilder.bind(update_queues).to(weibo).with("avatar.update");
//        admin.declareBinding(binding);

    }

}
