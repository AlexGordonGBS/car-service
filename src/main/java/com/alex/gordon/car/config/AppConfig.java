package com.alex.gordon.car.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class AppConfig {

    @Bean
    @SuppressWarnings({"deprecation", "WeakerAccess"})
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConFactory
            = new JedisConnectionFactory();
// REMOTE DB:        redis-cli -h redis-19133.c83.us-east-1-2.ec2.cloud.redislabs.com -p 19133 -a 9xPWdrDBxIl7UopMTCIzcOLItkAQwh9d
        jedisConFactory.setHostName("redis-19133.c83.us-east-1-2.ec2.cloud.redislabs.com");
        jedisConFactory.setPort(19133);
        jedisConFactory.setPassword("9xPWdrDBxIl7UopMTCIzcOLItkAQwh9d");
        return jedisConFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

}
