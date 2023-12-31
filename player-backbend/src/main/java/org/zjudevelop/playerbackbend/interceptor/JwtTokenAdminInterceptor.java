package org.zjudevelop.playerbackbend.interceptor;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.zjudevelop.playerbackbend.common.context.BaseContext;
import org.zjudevelop.playerbackbend.pojo.CheckAuth;
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
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 请求方式发生错误
//        if("/error".equals(request.getRequestURI())){
//            return false;
//        }

        // check annotation of CheckAuth
        HandlerMethod hm = (HandlerMethod) handler;
        boolean check = true;
        CheckAuth methodAuth = hm.getMethodAnnotation(CheckAuth.class);
        if (methodAuth != null) {
            check = methodAuth.check();
        }
        if (!check) {
            return true;
        }

        // 验证token
        String token  = request.getHeader(jwtProperties.getUserTokenName());
        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            Long userId = Long.valueOf(claims.get("user").toString());
            BaseContext.setCurrentUserId(userId);
            return true;
        } catch (Exception ex) {
            // 日志打印请求URI
            log.warn(request.getRequestURI() + " 请求失败，无有效token");

            response.setStatus(401);
            return false;
        }
    }
}
