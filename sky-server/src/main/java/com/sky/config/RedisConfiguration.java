package com.sky.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Select;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisConfiguration {
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("Create the RedisTemplate");
        RedisTemplate redisTemplate = new RedisTemplate();
        // Set the connection factory
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // Set the key serializer
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
