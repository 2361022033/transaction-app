package com.service.impl;

import com.controller.user.convert.UserConvert;
import com.controller.user.dto.req.UserAddReq;
import com.controller.user.dto.req.UserLoginReq;
import com.domain.entity.UserInfo;
import com.domain.mapper.UserInfoMapper;
import com.service.UserService;
import com.infrastructure.ServiceExcpetion;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    public static final String KEY_MD5 = "MD5";

    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     * 用户注册
     * @param req
     */
    @Override
    public void add(UserAddReq req) {
        UserInfo user = userInfoMapper.selectByUserName(req.getUserName());
        if (user!=null){
            throw new ServiceExcpetion(500,"用户名已被占用,请修改后重试!");
        }
        UserInfo in = UserConvert.INSTANCE.convert(req);
        try {
            MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
            md5.update(req.getPassword().getBytes(StandardCharsets.UTF_8));
            in.setPassword(Arrays.toString(md5.digest()));
        } catch (Exception e) {
            log.error("注册密码加密异常:", e);
            throw new ServiceExcpetion(500,"注册密码加密异常");
        }
        userInfoMapper.insertSelective(in);
    }

    /**
     * 登录
     * @param req
     */
    @Override
    public void login(UserLoginReq req) {
        UserInfo user = userInfoMapper.selectByUserName(req.getUserName());
        if (user==null){
            throw new ServiceExcpetion(500,"用户名不存在!");
        }
        String password = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
            md5.update(req.getPassword().getBytes(StandardCharsets.UTF_8));
            password = Arrays.toString(md5.digest());
        } catch (Exception e) {
            log.error("注册密码加密异常:", e);
            throw new ServiceExcpetion(500,"注册密码加密异常");
        }
        if (!StrUtil.equals(password,user.getPassword())){
            throw new ServiceExcpetion(500,"密码不正确");
        }
        log.info("用户名[{}],登录成功",user.getUserName());
        // 生成token

    }

    /**
     * id查询用户信息
     * @param userId
     * @return
     */
    @Override
    public UserInfo findDetailByUserId(Long userId) {
        return userInfoMapper.selectByUserId(userId);
    }



}
