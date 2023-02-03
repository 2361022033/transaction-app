package booktransaction.com.infrastructure.filter;

import booktransaction.com.domain.entity.UserInfo;
import booktransaction.com.domain.mapper.UserInfoMapper;
import booktransaction.com.infrastructure.HttpResult;
import booktransaction.com.infrastructure.ServiceExcpetion;
import booktransaction.com.service.UserService;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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


    @Component
    public static class UserAutoFilter extends OncePerRequestFilter{

        @Autowired
        private UserService userService;
        @Autowired
        private UserInfoMapper userInfoMapper;

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            try {
//                String userInfoBase64 = request.getHeader("Authorization");
                String userInfoBase64 = request.getHeader("token");
                String decodedString = new String(Base64.getDecoder().decode(userInfoBase64));
                TokenEntity tokenEntity = JSON.parseObject(decodedString, TokenEntity.class);
                if(tokenEntity.getOverdueTime().before(new Date())){
                    log.info("用户token超时,请重新登录!");
                    throw new ServiceExcpetion(500,"用户token超时,请重新登录!");
                }
                UserInfo userInfo = userInfoMapper.selectByUserName(tokenEntity.getUserName());
                if (Objects.isNull(userInfo)){
                    log.info("无效的token");
                    throw new ServiceExcpetion(500,"无效的token");
                }
                UserInfoCache.setUserInfo(userInfo);
            }catch (Exception e){
                log.error("用户信息过滤处理失败",e);
                setError(response, -1,"接口调用异常，请联系管理员");
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
