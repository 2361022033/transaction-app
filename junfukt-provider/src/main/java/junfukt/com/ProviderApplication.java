package junfukt.com;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author admin
 */
@Slf4j
@SpringBootApplication
public class ProviderApplication {
    public static void main(final String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
//        new SpringApplicationBuilder(ProviderApplication.class)
//                .web(WebApplicationType.NONE) // 非 Web 应用
//                .run(args);
        log.info("========== provider is success ==========");
    }

}