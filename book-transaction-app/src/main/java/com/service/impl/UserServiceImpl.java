package com.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson2.JSON;
import com.controller.user.convert.UserConvert;
import com.controller.user.dto.req.UserAddReq;
import com.controller.user.dto.req.UserLoginReq;
import com.domain.entity.UserInfo;
import com.domain.mapper.UserInfoMapper;
import com.infrastructure.ServiceExcpetion;
import com.infrastructure.filter.TokenEntity;
import com.infrastructure.filter.UserInfoCache;
import com.service.UserService;
import com.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserInfoMapper userInfoMapper;
    @Autowired
    RedisUtils redisUtils;

    /**
     * 用户注册
     *
     * @param req
     */
    @Override
    public void add(UserAddReq req) {
        UserInfo user = userInfoMapper.selectByUserName(req.getUserName());
        if (user != null) {
            throw new ServiceExcpetion(500, "用户名已被占用,请修改后重试!");
        }
        Object x = redisUtils.get("validCode:" + req.getUserName());
        if (Objects.isNull(x)){
            throw new ServiceExcpetion(500, "验证码已过期,请刷新后重新输入!");
        }
        if (!req.getValidCode().equals(x.toString())){
            throw new ServiceExcpetion(500, "验证码不正确!");
        }
        UserInfo in = UserConvert.INSTANCE.UserAddReq2Info(req);
        in.setPassword(DigestUtil.md5Hex(req.getPassword()));
        userInfoMapper.insertSelective(in);
    }

    /**
     * 登录
     *
     * @param req
     */
    @Override
    public void login(UserLoginReq req) {
        UserInfo user = userInfoMapper.selectByUserName(req.getUserName());
        if (user == null) {
            throw new ServiceExcpetion(500, "用户名不存在!");
        }
        String password = DigestUtil.md5Hex(req.getPassword());
        if (!StrUtil.equals(password, user.getPassword())) {
            throw new ServiceExcpetion(500, "密码不正确");
        }
        log.info("用户名[{}],登录成功", user.getUserName());
        UserInfoCache.setUserInfo(user);
        // 生成token
        Date date = new Date();
        Date overTime = new Date(date.getTime() + (1000 * 60 * 60));
        TokenEntity tokenEntity = new TokenEntity().setUserName(user.getUserName()).setRoot("book-transaction").setOverdueTime(overTime);
        String encodedString = Base64.getEncoder().encodeToString(JSON.toJSONString(tokenEntity).getBytes());
        log.info("用户名[{}]生成的token为:{}", user.getUserName(), encodedString);
    }

    /**
     * id查询用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserInfo findDetailByUserId(Long userId) {
        return userInfoMapper.selectByUserId(userId);
    }

    /**
     * 获取随机验证码
     * @return
     */
    @Override
    public String getValidCode(String userName) {
        String code = RandomStringUtils.randomNumeric(6);
        log.info("[{}]用户产生的验证码是[{}]" ,userName, code);
        redisUtils.set("validCode:"+userName,code,120L);
        return code;
    }


}
