package junfukt.com.controller;

import junfukt.com.controller.dto.UserAddReq;
import junfukt.com.controller.dto.UserResp;
import junfukt.com.controller.dto.UserUpdateReq;
import junfukt.com.service.UserService;
import junfukt.com.utils.BasePageReq;
import junfukt.com.utils.BasePageResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    @RequestMapping("/test")
    public void test() {
        System.out.println("测试成功");
        log.info("这是测试成功的slf4j日志:"+(20L/10L+ 1L));
    }

    @PostMapping(value = "/add", name = "用户注册")
    public void add(@Valid UserAddReq req) {
        userService.add(req);
    }

    @PostMapping(value = "/update", name = "编辑用户信息")
    public void update(@Valid UserUpdateReq req) {
        userService.update(req);
    }

    @GetMapping(value = "/page", name = "分页查询所有用户")
    public BasePageResp<UserResp> page(BasePageReq req) {
        return userService.page(req);
    }

    @GetMapping(value = "/selectUser", name = "查询用户信息")
    public UserResp selectUser(String userId) {
        return userService.selectUserByUserId(userId);
    }

}
