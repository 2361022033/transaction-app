package junfukt.com.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/test")
    public void test(){
        System.out.println("测试成功");
        log.info("这是测试成功的slf4j日志");
    }
}
