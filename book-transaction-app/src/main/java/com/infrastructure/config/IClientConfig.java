package com.infrastructure.config;

import com.netflix.client.config.DefaultClientConfigImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * nacos提示需要定义这个配置类
 */
@Configuration
public class IClientConfig {
 
    @Bean
    public DefaultClientConfigImpl iClientConfig(){
        return new DefaultClientConfigImpl();
    }
}