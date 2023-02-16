package com.controller.user;

import com.controller.user.convert.UserConvert;
import com.controller.user.dto.req.UserAddReq;
import com.controller.user.dto.req.UserLoginReq;
import com.controller.user.dto.resp.LookOtherResp;
import com.controller.user.dto.resp.MyInfoResp;
import com.infrastructure.HttpResult;
import com.infrastructure.filter.TokenEntity;
import com.service.UserService;
import com.utils.UserInfoUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson2.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@RestController
@RequestMapping("/user")
@Api(tags = {"用户接口"})
public class UserInfoController {
    @Resource
    private UserService userService;

    @ApiOperation(value = "测试")
    @RequestMapping(value = "/test",name = "测试",method = {RequestMethod.PUT,RequestMethod.GET})
    public HttpResult<String> test(String userId){
        try {
            System.out.println("配置文件："+ SpringUtil.getActiveProfile());
            SimpleDateFormat sdfymdTime = new SimpleDateFormat("yyyyMMddHHmmss");
            Date parse = sdfymdTime.parse("20240203170000");
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


    @ApiOperation(value = "用户注册")
    @PostMapping(value = "/add",name = "用户注册")
    public HttpResult<Void> add(UserAddReq req){
        userService.add(req);
        return HttpResult.success();
    }

    @ApiOperation(value = "用户登录")
    @PostMapping(value = "/login",name = "用户登录")
    public HttpResult<Void> login(@RequestBody UserLoginReq req){
        userService.login(req);
        return HttpResult.success();
    }
    @ApiOperation(value = "我的用户信息")
    @GetMapping(value = "/myInfo",name = "我的用户信息")
    public HttpResult<MyInfoResp> myInfo(){
        return HttpResult.success(UserConvert.INSTANCE.userInfo2MyInfoResp(userService.findDetailByUserId(UserInfoUtil.getUserId())));
    }

    @ApiOperation(value = "查看他人用户信息")
    @GetMapping(value = "/lookOther",name = "查看他人用户信息")
    public HttpResult<LookOtherResp> lookOther(@RequestParam("userId") Long userId){
        return HttpResult.success(UserConvert.INSTANCE.userInfo2LookOtherResp(userService.findDetailByUserId(userId)));
    }

}
