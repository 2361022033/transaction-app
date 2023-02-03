package booktransaction.com.service;

import booktransaction.com.controller.user.dto.req.UserAddReq;
import booktransaction.com.controller.user.dto.req.UserLoginReq;
import booktransaction.com.controller.user.dto.resp.LookOtherResp;

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
     * 查看他人用户信息
     * @param userId
     * @return
     */
    LookOtherResp lookOther(String userId);
}
