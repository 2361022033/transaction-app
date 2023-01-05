package junfukt.com.service;

import junfukt.com.controller.dto.UserAddReq;

public interface UserService {
    /**
     * 注册
     * @param user
     */
    void add(UserAddReq user);
}
