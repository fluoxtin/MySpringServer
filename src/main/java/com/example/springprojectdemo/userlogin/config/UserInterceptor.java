package com.example.springprojectdemo.userlogin.config;

import com.example.springprojectdemo.userlogin.service.UserService;
import com.example.springprojectdemo.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 同样在这里调用用户服务传入session，判断用户是否登录或者有效
        // 未登录则重定向至主页
//        if (!userService.isLogin(request.getSession()).isSuccess()) {
//            response.sendRedirect("/");
//            return false;
//        }
//        return true;
        String token = request.getHeader("token");
        if (TokenUtils.verify(token)) {
            System.out.println("true");
            return true;
        } else {
            System.out.println("false");
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