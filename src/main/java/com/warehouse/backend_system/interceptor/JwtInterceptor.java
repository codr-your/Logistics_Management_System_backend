package com.warehouse.backend_system.interceptor;

import com.warehouse.backend_system.util.JwtUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // =========毕设联调阶段：暂时关闭JWT校验，全部放行=========
        return true;

        /*
        String uri = request.getRequestURI();
        //登录接口直接放行
        if(uri.endsWith("/user/login")){
            return true;
        }
        String token = request.getHeader("token");
        if(token == null || !JwtUtil.validateToken(token)){
            response.setStatus(401);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"token失效，请重新登录\",\"data\":null}");
            return false;
        }
        return true;
        */
    }
}