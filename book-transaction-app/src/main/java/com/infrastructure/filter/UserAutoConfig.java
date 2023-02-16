package com.infrastructure.filter;

import com.domain.entity.UserInfo;
import com.domain.mapper.UserInfoMapper;
import com.infrastructure.HttpResult;
import com.infrastructure.ServiceExcpetion;
import com.service.UserService;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@Configuration
@Slf4j
public class UserAutoConfig {
    private static final String LOCAL_ENVIRONMENT = "dev";

    @Order(value = 101)
    @Aspect
    @Component
    public static class UserInfoClearAop {
        @Pointcut("within(com..*Controller)")
        public void pointCut() {
        }
        @After(value = "pointCut()")
        public void clear() {
            UserInfoCache.clear();
        }
    }

    @Component
    public static class UserAutoFilter extends OncePerRequestFilter{

        @Autowired
        private UserService userService;
        @Autowired
        private UserInfoMapper userInfoMapper;

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            if (!StringUtils.equalsIgnoreCase(LOCAL_ENVIRONMENT, SpringUtil.getActiveProfile())) {
                try {
                    // todo 应该是从请求头里获得token现在不正规   xml配置文件中环境为dev有默认用户
                    // String userInfoBase64 = request.getHeader("Authorization");
                    String userInfoBase64 = request.getHeader("token");
                    if (StrUtil.isBlank(userInfoBase64)){
                        throw new ServiceExcpetion(500, "token不能为空!");
                    }
                    String decodedString = new String(Base64.getDecoder().decode(userInfoBase64));
                    TokenEntity tokenEntity = JSON.parseObject(decodedString, TokenEntity.class);
                    if (tokenEntity.getOverdueTime().before(new Date())) {
                        log.info("用户token超时,请重新登录!");
                        throw new ServiceExcpetion(500, "用户token超时,请重新登录!");
                    }
                    UserInfo userInfo = userInfoMapper.selectByUserName(tokenEntity.getUserName());
                    if (Objects.isNull(userInfo)) {
                        log.info("无效的token");
                        throw new ServiceExcpetion(500, "无效的token");
                    }
                    UserInfoCache.setUserInfo(userInfo);
                } catch (Exception e) {
                    log.error("用户信息过滤处理失败", e);
                    setError(response, -1, "接口调用异常，请联系管理员");
                    return;
                }
            }
            filterChain.doFilter(request, response);
        }

        private void setError(HttpServletResponse response, int code, String message)  throws IOException {
            log.error(message);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType("application/json; charset=utf-8");
            HttpResult er = HttpResult.failure(code, message ,null);
            response.getWriter().write(new ObjectMapper().writeValueAsString(er));
        }
    }
}
