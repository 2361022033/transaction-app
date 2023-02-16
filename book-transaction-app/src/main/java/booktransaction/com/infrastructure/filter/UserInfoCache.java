package booktransaction.com.infrastructure.filter;

import booktransaction.com.domain.entity.UserInfo;

/**
 * 用户信息缓存
 */
public class UserInfoCache {

    private static final ThreadLocal<UserInfo> USER = new InheritableThreadLocal<>();

    public static UserInfo getUserInfo() {
        return USER.get();
    }

    public static void setUserInfo(UserInfo userInfo) {
        USER.set(userInfo);
    }

    public static void clear() {
        USER.remove();
    }
}
