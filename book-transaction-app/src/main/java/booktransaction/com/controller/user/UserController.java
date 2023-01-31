package booktransaction.com.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import booktransaction.com.controller.user.dto.UserAddReq;
import booktransaction.com.controller.user.dto.UserResp;
import booktransaction.com.controller.user.dto.UserUpdateReq;
import booktransaction.com.service.UserService;
import booktransaction.com.utils.BasePageReq;
import booktransaction.com.utils.BasePageResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Slf4j
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    @RequestMapping("/test")
    public void test() {
        log.info("这是测试成功的slf4j日志:"+(20L/10L+ 1L));
    }

    @ApiModelProperty(value = "用户注册")
    @PostMapping(value = "/add", name = "用户注册")
    public void add(@Valid UserAddReq req) {
        userService.add(req);
    }

    @ApiModelProperty(value = "编辑用户信息")
    @PostMapping(value = "/update", name = "编辑用户信息")
    public void update(@Valid UserUpdateReq req) {
        userService.update(req);
    }
    @ApiModelProperty("分页查询所有用户")
    @GetMapping(value = "/page", name = "分页查询所有用户")
    public BasePageResp<UserResp> page(BasePageReq req) {
        return userService.page(req);
    }
    @ApiModelProperty("查询用户信息")
    @GetMapping(value = "/selectUser", name = "查询用户信息")
    public UserResp selectUser(String userId) {
        return userService.selectUserByUserId(userId);
    }

}