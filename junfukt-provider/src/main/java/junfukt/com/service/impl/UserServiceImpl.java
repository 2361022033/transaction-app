package junfukt.com.service.impl;

import com.domain.entity.User;
import com.domain.mapper.UserMapper;
import junfukt.com.controller.dto.UserAddReq;
import junfukt.com.controller.dto.UserResp;
import junfukt.com.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 注册
     * @param req
     */
    @Override
    public void add(UserAddReq req) {
        User user = userMapper.selectByUserId(req.getUserId());
        if (Objects.nonNull(user)){
            throw new RuntimeException("用户号已存在!");
        }
        userMapper.insertSelective(new User()
                .setUserId(req.getUserId())
                .setRealName(req.getRealName())
                .setNickName(req.getNickName())
                .setStaffNo(req.getStaffNo())
                .setAvatar(req.getAvatar())
                .setIntroduction(req.getIntroduction()));
    }

    /**
     * 查询全部用户
     * 以后做分页框架 todo
     * @return
     */
    @Override
    public List<UserResp> selectAll() {
        List<User> users = userMapper.selectAll();
        List<UserResp> out = new ArrayList<UserResp>();
        for (User user:users) {
            UserResp userResp = new UserResp();
            BeanUtils.copyProperties(user,userResp);
            out.add(userResp);
        }
        return out;
    }
}
