package com.example.springprojectdemo.service.impl;

import com.example.springprojectdemo.dao.StudentDao;
import com.example.springprojectdemo.dao.UserDao;
import com.example.springprojectdemo.dataobject.*;
import com.example.springprojectdemo.model.Result;
import com.example.springprojectdemo.model.ResultCode;
import com.example.springprojectdemo.service.StudentService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class StudentServiceImpl implements StudentService {

    @Resource
    UserDao userDao;

    @Resource
    StudentDao studentDao;

    @Override
    public Result<Student> loginOrRegisterForS(User user) {
        Result<Student> result = new Result<>();
        User getUser = userDao.getByUserName(user.getUsername());
        if (getUser != null) {
            if (!getUser.getPassword().equals(DigestUtils.md5Hex(user.getPassword()))) {
                result.setResultCode(ResultCode.LOGIN_FAILED);
                return result;
            }
        } else {
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
            userDao.add(user);
        }

        Student student = studentDao.getStudentById(user.getUsername());

        result = Result.success(student);

        return result;
    }


    @Override
    public Result<Student> updateStudent(Student student) {
        Result<Student> result;
        studentDao.addStudent(student);
        result = Result.success(studentDao.getStudentById(student.getStu_id()));
        return result;
    }

    @Override
    public Result<List<Course>> getCourse(String stu_id) {
        return Result.success(studentDao.getCourses(stu_id));
    }

    @Override
    public Result<List<AttendanceRecord>> getAttendanceRecord(String stu_id) {
        return Result.success(studentDao.getAttendanceRecord(stu_id));
    }

    @Override
    public Result<AttendTask> getAttendTask(String stu_id) {
        AttendTask task = studentDao.getTask(stu_id);
        studentDao.deleteTask(stu_id);
        return Result.success(task);
    }
}
