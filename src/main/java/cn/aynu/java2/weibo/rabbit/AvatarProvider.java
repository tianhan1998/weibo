package cn.aynu.java2.weibo.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tianh
 */
@Component
public class AvatarProvider {
    private static final String AVATAR_EXCHANGE ="weibo_avatar";
    private static final String UPDATE_AVATAR_ROUTING_KEY="avatar.update";

    @Resource
    RabbitTemplate rabbitTemplate;

    public void updateAvatar(String avatarUrl,String id){
        Map<String, String> messages = new HashMap<>(2);
        messages.put("avatarUrl",avatarUrl);
        messages.put("id",id);
        rabbitTemplate.convertAndSend(AVATAR_EXCHANGE,UPDATE_AVATAR_ROUTING_KEY, messages);
    }

}
