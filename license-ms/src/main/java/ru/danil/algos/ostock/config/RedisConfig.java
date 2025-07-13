package ru.danil.algos.ostock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import ru.danil.algos.ostock.model.Organization;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Organization> redisTemplate(RedisConnectionFactory factory) {
        var template = new RedisTemplate<String, Organization>();
        template.setConnectionFactory(factory);

        // Сериализатор для ключа (String)
        template.setKeySerializer(new StringRedisSerializer());

        // Сериализатор для значения (User в JSON)
        Jackson2JsonRedisSerializer<Organization> serializer = new Jackson2JsonRedisSerializer<>(Organization.class);
        template.setValueSerializer(serializer);
        return template;
    }
}
