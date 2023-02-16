package com.utils;

import com.domain.entity.UserInfo;
import com.infrastructure.ServiceExcpetion;
import com.infrastructure.filter.UserInfoCache;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.druid.util.StringUtils;

import java.util.Objects;

/**
 *
 */
public class UserInfoUtil {

    private static final String LOCAL_ENVIRONMENT = "dev";

    private static final Long DEFAULT_USER_ID = 1L;

    private static final String DEFAULT_REAL_NAME = "苏俊辅";

    private static final String DEFAULT_NICK_NAME = "苏悟空";

    private static final String DEFAULT_USER_NAME = "001";

    /**
     * 获取当前登录用户ID
     *
     * @return
     */
    public static Long getUserId() {
        UserInfo userInfo = getUserInfo();
        if (Objects.nonNull(userInfo)) {
            return userInfo.getId();
        }
        if (StringUtils.equalsIgnoreCase(LOCAL_ENVIRONMENT, SpringUtil.getActiveProfile())) {
            return DEFAULT_USER_ID;
        }
        throw new ServiceExcpetion(500,"无法取到ThreadLoacl缓存中的用户id");
    }

    /**
     * 真实姓名
     * @return
     */
    public static String getRealName() {
        UserInfo userInfo = getUserInfo();
        if (Objects.nonNull(userInfo)) {
            return userInfo.getRealName();
        }
        if (StringUtils.equalsIgnoreCase(LOCAL_ENVIRONMENT, SpringUtil.getActiveProfile())) {
            return DEFAULT_REAL_NAME;
        }
        throw new ServiceExcpetion(500,"无法取到ThreadLoacl缓存中的用户真实姓名");
    }

    /**
     * 用户号
     * @return
     */
    public static String getStaffNo() {
        UserInfo userInfo = getUserInfo();
        if (Objects.nonNull(userInfo)) {
            return userInfo.getUserName();
        }
        if (StringUtils.equalsIgnoreCase(LOCAL_ENVIRONMENT, SpringUtil.getActiveProfile())) {
            return DEFAULT_USER_NAME;
        }
        throw new ServiceExcpetion(500,"无法取到ThreadLoacl缓存中的用户号");
    }


    /**
     * 获取当前登录用户姓名
     *
     * @return
     */
    public static String getNickName() {
        UserInfo userInfo = getUserInfo();
        if (Objects.nonNull(userInfo)) {
            return userInfo.getNickName();
        }
        if (StringUtils.equalsIgnoreCase(LOCAL_ENVIRONMENT, SpringUtil.getActiveProfile())) {
            return DEFAULT_NICK_NAME;
        }
        throw new ServiceExcpetion(500,"无法取到ThreadLoacl缓存中的用户昵称");
    }

    public static UserInfo getUserInfo() {
        UserInfo userInfo = UserInfoCache.getUserInfo();
        if(userInfo != null){
            return userInfo;
        }
        if (StringUtils.equalsIgnoreCase(LOCAL_ENVIRONMENT, SpringUtil.getActiveProfile())) {
            userInfo = new UserInfo();
            userInfo.setId(DEFAULT_USER_ID);
            userInfo.setAvatar("");
            userInfo.setRealName(DEFAULT_REAL_NAME);
            userInfo.setNickName(DEFAULT_NICK_NAME);
            userInfo.setUserName(DEFAULT_USER_NAME);
            return userInfo;
        }
        throw new ServiceExcpetion(500,"无法取到ThreadLoacl缓存中的用户信息");
    }

}
