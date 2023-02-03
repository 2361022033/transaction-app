package booktransaction.com.controller.user;

import booktransaction.com.controller.user.dto.req.UserAddReq;
import booktransaction.com.controller.user.dto.req.UserLoginReq;
import booktransaction.com.controller.user.dto.resp.LookOtherResp;
import booktransaction.com.domain.entity.UserInfo;
import booktransaction.com.infrastructure.HttpResult;
import booktransaction.com.infrastructure.filter.TokenEntity;
import booktransaction.com.service.UserService;
import com.alibaba.fastjson2.JSON;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin
public class UserInfoController {
    @Resource
   private UserService userService;

    public static void main(String[] args) {
        log.info("测试="+new Date());
        try {
            SimpleDateFormat sdfymdTime = new SimpleDateFormat("yyyyMMddHHmmss");
            Date parse = sdfymdTime.parse("20230203170000");
            TokenEntity tokenEntity = new TokenEntity().setUserName("001").setRoot("book-transaction").setOverdueTime(parse);
            String tokenS = JSON.toJSONString(tokenEntity);
            String encodedString = Base64.getEncoder().encodeToString(tokenS.getBytes());
            System.out.println(encodedString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @ApiModelProperty("测试")
    @PostMapping(value = "/test",name = "测试")
    public HttpResult<String> test(String userId){
        log.info("测试="+new Date());
        try {
            SimpleDateFormat sdfymdTime = new SimpleDateFormat("yyyyMMddHHmmss");
            Date parse = sdfymdTime.parse("20230203170000");
            TokenEntity tokenEntity = new TokenEntity().setUserName(userId).setRoot("book-transaction").setOverdueTime(parse);
            String tokenS = JSON.toJSONString(tokenEntity);
            String encodedString = Base64.getEncoder().encodeToString(tokenS.getBytes());
            System.out.println(encodedString);
            return HttpResult.success(encodedString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
