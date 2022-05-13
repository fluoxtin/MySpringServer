package com.example.springprojectdemo.api;

import com.example.springprojectdemo.dataobject.*;
import com.example.springprojectdemo.model.Result;
import com.example.springprojectdemo.service.StudentService;
import com.example.springprojectdemo.util.TokenUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/student")
public class StudentAPI {

    @Resource
    StudentService studentService;

    /**
     *  user register
     * @param user user info
     * @return 注册结果
     */
    @PostMapping("/login")
    public Result<Student> loginOrRegisterForS(@RequestBody @Valid User user, HttpServletResponse response) {
        Result<Student> result;

        result = studentService.loginOrRegisterForS(user);
        if (result.getCode() == 200) {
            response.addHeader("Access-Control-Expose-headers", "token");
            response.addHeader("token", TokenUtils.create(user.getUsername(), user.getPassword()));
        }

        return result;
    }

    @PostMapping("/update")
    public Result<Student> updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @PostMapping("/getcourses")
    public Result<List<Course>> getCourses(String stu_id){
        return studentService.getCourse(stu_id);
    }

    @PostMapping("/getRecord")
    public Result<List<AttendanceRecord>> getRecord(String stu_id) {
        return studentService.getAttendanceRecord(stu_id);
    }

    @PostMapping("/gettask")
    public Result<AttendTask> getTask(String stu_id) {
        return studentService.getAttendTask(stu_id);
    }

    @PostMapping("/getInfo")
    public Result<Student> getInfo(String stu_id) {
        return studentService.getInfoById(stu_id);
    }

}
