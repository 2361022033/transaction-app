//package com.infrastructure.filter;
//
//import cn.hutool.jwt.Claims;
//
//import cn.hutool.jwt.JWT;
//import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Date;
//
//@Component
//public class JwtUtils {
//
////    设置token过期时间
//    public static final long EXPIRE = 1000 * 60 * 60 * 24;
////    密钥，随便写，做加密操作
//    public static final String APP_SECRET ="xbrceXUKwYIRoQJndTPFNzAmhDagkLMExbrceXUKwYIRoQJndTPFNzAmhDagkLME";
//
////    生成jwt字符串的方法
//    public static String getJwtToken(String id, String nickname){
//        JWT jwt = JWT.create();
//
//                jwt
//                //设置过期时间
//                .setSubject("guli-user")//名字随便取
//                .setIssuedAt(new Date())
//                .setExpiresAt(new Date(System.currentTimeMillis() + EXPIRE));
//                jwt.sign(, APP_SECRET);
//                //设置jwt主体部分
//                //根据密钥生成字符串
//
//        return JwtToken;
//    }
//
//    /**
//     * 判断jwt是否存在与有效
//     * @param request
//     * @return
//     */
//    public static boolean checkToken(HttpServletRequest request) {
//        try {
//            String jwtToken = request.getHeader("Cookie").split("=")[1];
//            if(StringUtils.isEmpty(jwtToken)) return false;
//            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//    /**
//     * 根据jwt获取用户信息
//     * @param request
//     * @return
//     */
//    public static Claims getMemberByJwtToken(HttpServletRequest request) {
//        String jwtToken = request.getHeader("Cookie").split("=")[1];
//        if(StringUtils.isEmpty(jwtToken)) return null;
//        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
//        Claims claims = claimsJws.getBody();
//        return claims;
//    }
//}
