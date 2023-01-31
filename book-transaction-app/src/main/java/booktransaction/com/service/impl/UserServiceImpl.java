package booktransaction.com.service.impl;

import booktransaction.com.controller.user.convert.UserConvert;
import booktransaction.com.controller.user.dto.UserAddReq;
import booktransaction.com.controller.user.dto.UserResp;
import booktransaction.com.controller.user.dto.UserUpdateReq;
import booktransaction.com.domain.entity.User;
import booktransaction.com.domain.mapper.UserMapper;
import booktransaction.com.service.UserService;
import booktransaction.com.utils.BasePageReq;
import booktransaction.com.utils.BasePageResp;
import booktransaction.com.utils.PageResultUtils;
import booktransaction.com.utils.ServiceExcpetion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 注册
     *
     * @param req
     */
    @Override
    public void add(UserAddReq req) {
        User user = userMapper.selectByUserId(req.getUserId());
        if (Objects.nonNull(user)) {
            throw new ServiceExcpetion(500,"用户号已存在!");
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
     * 编辑用户信息
     *
     * @param req
     */
    @Override
    public void update(UserUpdateReq req) {
        User user = userMapper.selectByUserId(req.getUserId());
        if (Objects.isNull(user)) {
            throw new ServiceExcpetion(400,"用户号不存在!");
        }
        req.setId(user.getId());
        Long i = userMapper.updateByUsetId(req);
        if (i < 1) {
            throw new ServiceExcpetion(400, "用户信息更新失败");
        }

    }

    /**
     * 分页查询全部用户
     *
     * @return
     */
    @Override
    public BasePageResp<UserResp> page(BasePageReq req) {
        Long count = userMapper.countPageList(req);
        if (count < 1) {
            return PageResultUtils.defaultPageResult(req);
        }
        List<User> users = userMapper.selectPageList(req);
        List<UserResp> userRespList = UserConvert.INSTANCE.convert(users);
        return PageResultUtils.buildPageResult(req, count, userRespList);
    }

    /**
     * 查询用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserResp selectUserByUserId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new ServiceExcpetion(500,"用户号不能为空");
        }
        User user = userMapper.selectByUserId(userId);
        if (Objects.isNull(user)) {
            return null;
        }
        UserResp userResp = new UserResp();
        // spring自带的属性赋值
        BeanUtils.copyProperties(user, userResp);
        return userResp;
    }
}
