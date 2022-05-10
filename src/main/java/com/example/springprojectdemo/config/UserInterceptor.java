package com.example.springprojectdemo.config;

import com.example.springprojectdemo.model.Result;
import com.example.springprojectdemo.model.ResultCode;
import com.example.springprojectdemo.util.TokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");
        if (TokenUtils.verify(token)) {
            System.out.println("true");
            response.addHeader("Access-Control-Expose-headers", "token");
            response.addHeader("token", TokenUtils.getRefreshToken(token));
            return true;
        } else {
            System.out.println("false");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(
                    new Result<>(ResultCode.INVALIDATE_TOKEN)
            ));
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}