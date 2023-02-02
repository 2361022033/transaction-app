package booktransaction.com.controller.user;

import booktransaction.com.controller.user.dto.req.UserAddReq;
import booktransaction.com.controller.user.dto.req.UserLoginReq;
import booktransaction.com.infrastructure.HttpResult;
import booktransaction.com.service.UserService;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin
public class UserInfoController {
    @Resource
   private UserService userService;

    @ApiModelProperty("用户注册")
    @PostMapping(value = "/add",name = "用户注册")
    public void add(UserAddReq req){
        userService.add(req);
    }

    @ApiModelProperty("用户登录")
    @PostMapping(value = "/login",name = "用户登录")
    public HttpResult login(@RequestBody UserLoginReq req){
        userService.login(req);
        return HttpResult.success();
    }

}
