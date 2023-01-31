package booktransaction.com.service;

import booktransaction.com.controller.user.dto.req.UserAddReq;
import booktransaction.com.controller.user.dto.req.UserLoginReq;

public interface UserService {
    /**
     * 用户注册
     * @param req
     */
    void add(UserAddReq req);

    void login(UserLoginReq req);
}
