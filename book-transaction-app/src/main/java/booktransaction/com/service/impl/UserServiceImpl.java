package booktransaction.com.service.impl;

import booktransaction.com.controller.user.convert.UserConvert;
import booktransaction.com.controller.user.dto.req.UserAddReq;
import booktransaction.com.controller.user.dto.req.UserLoginReq;
import booktransaction.com.controller.user.dto.resp.LookOtherResp;
import booktransaction.com.domain.entity.UserInfo;
import booktransaction.com.domain.mapper.UserInfoMapper;
import booktransaction.com.service.UserService;
import booktransaction.com.infrastructure.ServiceExcpetion;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    public static final String KEY_MD5 = "MD5";

    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     * 用户注册
     * @param req
     */
    @Override
    public void add(UserAddReq req) {
        UserInfo user = userInfoMapper.selectByUserName(req.getUserName());
        if (user!=null){
            throw new ServiceExcpetion(500,"用户名已被占用,请修改后重试!");
        }
        UserInfo in = UserConvert.INSTANCE.convert(req);
        try {
            MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
            md5.update(req.getPassword().getBytes(StandardCharsets.UTF_8));
            in.setPassword(Arrays.toString(md5.digest()));
        } catch (Exception e) {
            log.error("注册密码加密异常:", e);
            throw new ServiceExcpetion(500,"注册密码加密异常");
        }
        userInfoMapper.insertSelective(in);
    }

    /**
     * 登录
     * @param req
     */
    @Override
    public void login(UserLoginReq req) {
        UserInfo user = userInfoMapper.selectByUserName(req.getUserName());
        if (user==null){
            throw new ServiceExcpetion(500,"用户名不存在!");
        }
        String password = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
            md5.update(req.getPassword().getBytes(StandardCharsets.UTF_8));
             password = Arrays.toString(md5.digest());
        } catch (Exception e) {
            log.error("注册密码加密异常:", e);
            throw new ServiceExcpetion(500,"注册密码加密异常");
        }
        if (!StrUtil.equals(password,user.getPassword())){
            throw new ServiceExcpetion(500,"密码不正确");
        }
        log.info("用户名[{}],登录成功",user.getUserName());
    }

    /**
     * 查看他人用户信息
     * @param userId
     * @return
     */
    @Override
    public LookOtherResp lookOther(String userId) {
        return UserConvert.INSTANCE.convert(userInfoMapper.selectByPrimaryKey(userId));
    }


}
