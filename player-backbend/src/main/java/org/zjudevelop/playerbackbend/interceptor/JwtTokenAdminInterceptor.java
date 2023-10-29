package org.zjudevelop.playerbackbend.interceptor;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.zjudevelop.playerbackbend.pojo.JwtProperties;
import org.zjudevelop.playerbackbend.utils.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 校验jwt
     *
     * @param request
     * @param response
     * @param handler
     * */
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 只拦截controller的方法
        // TODO: 区分需要鉴权和不需要鉴权的方法
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 验证token
        String token  = request.getHeader(jwtProperties.getUserTokenName());
        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            Long userId = Long.valueOf(claims.get("user").toString());
            log.info("当前用户id： " + userId);
            return true;
        } catch (Exception ex) {
            response.setStatus(401);
            return false;
        }
    }
}
