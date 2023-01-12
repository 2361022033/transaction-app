package junfukt.com;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author admin
 */
@Slf4j
@MapperScan("com.domain.mapper")
//@ComponentScan("junfukt.com.controller")
@SpringBootApplication
public class ProviderApplication {
    public static void main(final String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
        log.info("========== provider is success ==========");
    }

}