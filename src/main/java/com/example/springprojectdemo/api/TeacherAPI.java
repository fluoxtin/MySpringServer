package com.example.springprojectdemo.api;

import com.example.springprojectdemo.dataobject.AttendTask;
import com.example.springprojectdemo.dataobject.Course;
import com.example.springprojectdemo.dataobject.Teacher;
import com.example.springprojectdemo.dataobject.User;
import com.example.springprojectdemo.model.Result;
import com.example.springprojectdemo.service.TeacherService;
import com.example.springprojectdemo.util.TokenUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherAPI {

    @Resource
    TeacherService teacherService;

    @PostMapping("/login")
    public Result<Teacher> loginOrRegisterForT(@RequestBody @Valid User user, HttpServletResponse response) {
        Result<Teacher> result;

        result = teacherService.loginOrRegisterForT(user);
        if (result.getCode() == 200) {
            response.addHeader("Access-Control-Expose-headers", "token");
            response.addHeader("token", TokenUtils.create(user.getUsername(), user.getPassword()));
        }
        return result;
    }

    @PostMapping("/update")
    public Result<Teacher> updateTeacher(@RequestBody Teacher teacher) {
        return teacherService.updateTeacher(teacher);
    }

    @PostMapping("/getcourses")
    public Result<List<Course>> getCourses(HttpServletRequest request) {
        String tea_id = TokenUtils.getUsernameFromToken(request.getHeader("token"));
        return teacherService.getCourse(tea_id);
    }

    @PostMapping("/posttask")
    public Result postAttendanceTask(@RequestBody AttendTask task) {
        return teacherService.postTask(task);
    }

    @PostMapping("/getInfo")
    public Result<Teacher> getInfo(HttpServletRequest request) {
        String tea_id = TokenUtils.getUsernameFromToken(request.getHeader("token"));
        return teacherService.getTeacherById(tea_id);
    }

}
