package cn.aynu.java2.weibo.config;

import cn.aynu.java2.weibo.entity.Result;
import cn.aynu.java2.weibo.entity.User;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author tianh
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, User> userRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,User> template=new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new FastJsonRedisSerializer<>(User.class));
        return template;
    }
    @Bean
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object,Object> template=new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        return template;
    }
    @Bean
    public RedisTemplate<String, Result<Object>> resultRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Result<Object>> resultRedisTemplate=new RedisTemplate<>();
        resultRedisTemplate.setConnectionFactory(redisConnectionFactory);
        resultRedisTemplate.setValueSerializer(new FastJsonRedisSerializer<>(Result.class));
        resultRedisTemplate.setKeySerializer(new StringRedisSerializer());
        return resultRedisTemplate;
    }
}
