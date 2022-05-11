package com.example.springprojectdemo.api;

import com.example.springprojectdemo.dataobject.Student;
import com.example.springprojectdemo.dataobject.Teacher;
import com.example.springprojectdemo.dataobject.User;
import com.example.springprojectdemo.model.Result;
import com.example.springprojectdemo.service.UserService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author fluoxtin created on 2022/4/9
 */

@RestController
@RequestMapping("/api")
public class UserAPI {

    @Resource
    private UserService userService;

    /**
     *  user register
     * @param user user info
     * @return 注册结果
     */
    @PostMapping("/student/login")
    public Result<Student> loginOrRegisterForS(@RequestBody @Valid User user, HttpServletResponse response) {
        Result<Student> result;

        result = userService.loginOrRegisterForS(user);

        return result;
    }

    @PostMapping("/teacher/login")
    public Result<Teacher> loginOrRegisterForT(@RequestBody @Valid User user, BindingResult errors) {
        Result<Teacher> result;

        result = userService.loginOrRegisterForT(user);

        return result;
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
        Result<User> result;

        result = userService.update(user);

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

}

