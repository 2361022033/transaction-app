package junfukt.com.service;

import junfukt.com.controller.dto.UserAddReq;
import junfukt.com.controller.dto.UserResp;
import junfukt.com.controller.dto.UserUpdateReq;

import java.util.List;

public interface UserService {
    /**
     * 注册
     * @param user
     */
    void add(UserAddReq user);

    /**
     * 编辑用户信息
     * @param req
     */
    void update(UserUpdateReq req);

    /**
     * 查询全部用户列表
     * @return
     */
    List<UserResp> selectAll();

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    UserResp selectUserByUserId(String userId);

}
