package com.service;

import com.controller.user.dto.req.UserAddReq;
import com.controller.user.dto.req.UserLoginReq;
import com.domain.entity.UserInfo;

public interface UserService {


    /**
     * 用户注册
     * @param req
     */
    void add(UserAddReq req);

    /**
     * 登录
     * @param req
     */
    void login(UserLoginReq req);

    /**
     * id查询用户信息
     * @param userId
     * @return
     */
    UserInfo findDetailByUserId(Long userId);

}
