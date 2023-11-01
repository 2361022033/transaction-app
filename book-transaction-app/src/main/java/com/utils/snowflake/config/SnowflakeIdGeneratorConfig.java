package com.utils.snowflake.config;

import com.utils.snowflake.IdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Configuration
public class SnowflakeIdGeneratorConfig {

    @Value("${spring.application.name:}")
    private String applicationName;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @PostConstruct
    public void init() {
        IdGenerator.init(stringRedisTemplate, applicationName);
    }

}
