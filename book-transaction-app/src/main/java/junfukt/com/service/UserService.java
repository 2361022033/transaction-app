package junfukt.com.service;

import junfukt.com.controller.user.dto.UserAddReq;
import junfukt.com.controller.user.dto.UserResp;
import junfukt.com.controller.user.dto.UserUpdateReq;
import junfukt.com.utils.BasePageReq;
import junfukt.com.utils.BasePageResp;

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
     * 分页查询全部用户列表
     * @return
     */
    BasePageResp<UserResp> page(BasePageReq req);

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    UserResp selectUserByUserId(String userId);

}
