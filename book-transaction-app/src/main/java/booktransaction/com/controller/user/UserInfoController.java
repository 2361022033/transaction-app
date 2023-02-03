package booktransaction.com.controller.user;

import booktransaction.com.controller.user.dto.req.UserAddReq;
import booktransaction.com.controller.user.dto.req.UserLoginReq;
import booktransaction.com.controller.user.dto.resp.LookOtherResp;
import booktransaction.com.infrastructure.HttpResult;
import booktransaction.com.service.UserService;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin
public class UserInfoController {
    @Resource
   private UserService userService;

    @ApiModelProperty("测试")
    @PostMapping(value = "/test",name = "测试")
    public HttpResult test(){
        log.info("测试="+new Date());
        return HttpResult.success();
    }


    @ApiModelProperty("用户注册")
    @PostMapping(value = "/add",name = "用户注册")
    public HttpResult add(UserAddReq req){
        userService.add(req);
        return HttpResult.success();
    }

    @ApiModelProperty("用户登录")
    @PostMapping(value = "/login",name = "用户登录")
    public HttpResult login(@RequestBody UserLoginReq req){
        userService.login(req);
        return HttpResult.success();
    }

    @ApiModelProperty("我的用户信息")
    @GetMapping(value = "/myInfo",name = "我的用户信息")
    public HttpResult myInfo(){
        return HttpResult.success();
    }

    @ApiModelProperty("查看他人用户信息")
    @GetMapping(value = "/lookOther",name = "查看他人用户信息")
    public HttpResult<LookOtherResp> lookOther(@RequestParam("userId") String userId){
        return HttpResult.success(userService.lookOther(userId));
    }

}
