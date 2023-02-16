package com;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author admin
 */
@Slf4j
//@MapperScan("com.domain.mapper") //如果mapper扫描不到就加此注解
@SpringBootApplication
public class ProviderApplication {
    public static void main(final String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
        log.info("========== provider is success ==========");
    }

}