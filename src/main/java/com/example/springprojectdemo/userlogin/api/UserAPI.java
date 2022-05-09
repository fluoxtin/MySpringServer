package com.example.springprojectdemo.userlogin.api;

import com.example.springprojectdemo.userlogin.dataobject.Student;
import com.example.springprojectdemo.userlogin.dataobject.Teacher;
import com.example.springprojectdemo.userlogin.dataobject.User;
import com.example.springprojectdemo.userlogin.model.Result;
import com.example.springprojectdemo.userlogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author fluoxtin created on 2022/4/9
 */

@RestController
public class UserAPI {
    private static final String SESSION_NAME = "userInfo";

    @Autowired
    private UserService userService;

    /**
     *  user register
     * @param user user info
     * @param errors Validation  校验错误存放对象
     * @param request 请求对象 用于操作 session
     * @return 注册结果
     */
    @PostMapping("/login")
    public Result loginOrRegister( @Valid User user, BindingResult errors, HttpServletRequest request) {
        Result result;
        if (errors.hasErrors()) {
            result = new Result<>();
            result.setResultFailed(errors.getFieldError().getDefaultMessage());
            return result;
        }
        result = userService.loginOrRegister(user);
        System.out.println(result.getDate().toString());
        System.out.println(result.toString());
        return result;
    }

//    /**
//     *
//     * @param user user info
//     * @param errors Validation 校验错误存放对象
//     * @param request 请求对象 用于操作session
//     * @return 登录结果
//     */
//    @PostMapping("/login")
//    public Result<User> login( @Valid User user, BindingResult errors, HttpServletRequest request) {
//        Result<User> result;
//        if (errors.hasErrors()) {
//            result = new Result<>();
//            result.setResultFailed(errors.getFieldError().getDefaultMessage());
//        }
//        result = userService.login(user);
//
//        if (result.isSuccess()) {
//            request.getSession().setAttribute(SESSION_NAME, result.getDate());
//        }
//
//        return result;
//    }

    /**
     * 判断用户是否登录成功
     * @param request 请求对象 从中获取session里面的用户信息以判断用户是否登录
     * @return 结果对象 已经登录则结果为成功，且数据体为用户信息；否则结果为失败，数据体为空
     */
    @GetMapping("/islogin")
    public Result<User> isLogin(HttpServletRequest request) {
        // 传入 session 到用户服务层
        return userService.isLogin(request.getSession());
    }

    /**
     *
     * @param user 修改后用户名对象
     * @param request 请求对象 用于操作 session
     * @return 修改对象
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/update/user")
    public Result<User> update(User user, HttpServletRequest request) throws Exception {
        Result<User> result = new Result<>();
        HttpSession session = request.getSession();
//        User sessionUser = (User) session.getAttribute(SESSION_NAME);
//        if (sessionUser.getId() != user.getId()) {
//            result.setResultFailed("当前登录用户和被修改用户不一致，终止！");
//            return result;
//        }
        result = userService.update(user);
        if (result.isSuccess()) {
            session.setAttribute(SESSION_NAME, result.getDate());
        }
        return result;
    }

    @PostMapping("/student/update")
    public Result<Student> updateStudent(@RequestBody Student student) {
        return userService.updateStudent(student);
    }

    @PostMapping("/teacher/update")
    public Result<Teacher> updateTeacher(Teacher teacher) {
        return userService.updateTeacher(teacher);
    }

    @GetMapping("/logout")
    public Result logout(HttpServletRequest request) {
        Result result = new Result();
        // 把 session 里面的用户消息设为 null
        request.getSession().setAttribute(SESSION_NAME, null);
        result.setResultSuccess("用户退出登录成功", null);
        return result;
    }
}

// eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6IjI1ZDU1YWQyODNhYTQwMGFmNDY0Yzc2ZDcxM2MwN2FkIiwidXNlcm5hbWUiOiIxMDExMjM0NTYzIn0.H_0zPNwCYo3m8cXVpwaVGeZYcPeXL4CHcOI9athkzaU
