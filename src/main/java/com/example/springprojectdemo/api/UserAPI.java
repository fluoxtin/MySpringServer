package com.example.springprojectdemo.api;

import com.example.springprojectdemo.dataobject.Student;
import com.example.springprojectdemo.dataobject.Teacher;
import com.example.springprojectdemo.dataobject.User;
import com.example.springprojectdemo.model.Result;
import com.example.springprojectdemo.service.UserService;
import com.example.springprojectdemo.util.TokenUtils;
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
        if (result.getCode() == 200) {
            response.addHeader("Access-Control-Expose-headers", "token");
            response.addHeader("token", TokenUtils.create(user.getUsername(), user.getPassword()));
        }

        return result;
    }

    @PostMapping("/teacher/login")
    public Result<Teacher> loginOrRegisterForT(@RequestBody @Valid User user, HttpServletResponse response) {
        Result<Teacher> result;

        result = userService.loginOrRegisterForT(user);
        if (result.getCode() == 200) {
            response.addHeader("Access-Control-Expose-headers", "token");
            response.addHeader("token", TokenUtils.create(user.getUsername(), user.getPassword()));
        }
        return result;
    }

    /**
     *
     * @param user 修改后用户名对象
     * @return 修改对象
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/update/user")
    public Result<User> update(User user) throws Exception {
        Result<User> result;

        result = userService.update(user);

        return result;
    }

    @PostMapping("/student/update")
    public Result<Student> updateStudent(@RequestBody Student student) {
        return userService.updateStudent(student);
    }

    @PostMapping("/teacher/update")
    public Result<Teacher> updateTeacher(@RequestBody Teacher teacher) {
        return userService.updateTeacher(teacher);
    }

    @PostMapping("/islogin")
    public Result<User> login(HttpServletRequest request) {

        String token = request.getHeader("token");
        String id = TokenUtils.getUsernameFromToken(token);


        return userService.getUserById(id);
    }

}

