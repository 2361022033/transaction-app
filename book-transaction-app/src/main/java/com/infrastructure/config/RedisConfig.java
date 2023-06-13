package com.infrastructure.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @description: RedisConfig
 * @author: sjf
 * @date: 2022/5/26 10:00 上午
 */
@Configuration
@EnableCaching
public class RedisConfig {

    /**
     * 对rediskv序列化
     *
     * @param factory
     * @return
     */
    @Bean(value = "redisTemplateSerializer")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer<Object> j2jrs = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 解决jackson2无法反序列化LocalDateTime的问题
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.registerModule(new JavaTimeModule());
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        j2jrs.setObjectMapper(om);
        // 序列化 value 时使用此序列化方法
        template.setValueSerializer(j2jrs);
        template.setHashValueSerializer(j2jrs);
        // 序列化 key 时
        StringRedisSerializer srs = new StringRedisSerializer();
        template.setKeySerializer(srs);
        template.setHashKeySerializer(srs);
        template.afterPropertiesSet();
        return template;
    }


//    /**
//     * 通过注解来使用reids,使@Cacheable(value = "product:seckill", key = "#req.bookName")生效 所以配置这个bean,可是这个不能设置缓存默认过期时间
//     * @param redisConnectionFactory
//     * @return
//     */
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
//        return RedisCacheManager.create(redisConnectionFactory);
//    }

    /**
     * 配置一个CacheManager才能使用@Cacheable等注解
     *
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate<String, Object> template) {

        // 基本配置
        RedisCacheConfiguration defaultCacheConfiguration =
                RedisCacheConfiguration
                        .defaultCacheConfig()
                        // 设置key为String
                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(template.getStringSerializer()))
                        // 设置value 为自动转Json的Object
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(template.getValueSerializer()))
                        // 不缓存null
                        .disableCachingNullValues()
                        // 缓存数据保存1小时
                        .entryTtl(Duration.ofSeconds(60));

        // 构造一个redis缓存管理器
        RedisCacheManager redisCacheManager =
                RedisCacheManager.RedisCacheManagerBuilder
                        // Redis 连接工厂
                        .fromConnectionFactory(template.getConnectionFactory())
                        // 缓存配置
                        .cacheDefaults(defaultCacheConfiguration)
//                        .withInitialCacheConfigurations(Maps.newHashMap())
//                        .withCacheConfiguration("cache_user", getCacheConfigurationWithTtl(template, 60))
//                        .withCacheConfiguration("cache_post", getCacheConfigurationWithTtl(template, 120))
//                        // 上面默然缓存时间是1小时，但是可以根据cacheName来设置缓存时间
                        // 配置同步修改或删除 put/evict
                        .transactionAware()
                        .build();
        return redisCacheManager;
    }
}
