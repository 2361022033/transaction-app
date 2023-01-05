package junfukt.com.service;

import junfukt.com.controller.dto.UserAddReq;
import junfukt.com.controller.dto.UserResp;

import java.util.List;

public interface UserService {
    /**
     * 注册
     * @param user
     */
    void add(UserAddReq user);

    /**
     * 查询全部用户列表
     * @return
     */
    List<UserResp> selectAll();
}
