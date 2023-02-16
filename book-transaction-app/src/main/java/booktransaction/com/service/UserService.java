package booktransaction.com.service;

import booktransaction.com.controller.user.dto.req.UserAddReq;
import booktransaction.com.controller.user.dto.req.UserLoginReq;
import booktransaction.com.domain.entity.UserInfo;

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
