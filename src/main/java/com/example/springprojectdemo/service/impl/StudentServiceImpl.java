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
        StudentTask task = studentDao.getTask(stu_id);
        if (task != null) {
            String cour_id = studentDao.getCourIdFromCR(task.getAttend_id());
            AttendTask attendTask = new AttendTask(
                    task.getAttend_id(),
                    cour_id,
                    task.getDeadline(),
                    new Location(task.getLatitude(), task.getLongitude())
            );
            return Result.success(attendTask);
        } else return Result.success();


    }

    @Override
    public Result<Student> getInfoById(String stu_id) {
        Student student = studentDao.getStudentById(stu_id);
        if (student != null) {
            return Result.success(student);
        } else return Result.failed();
    }

    @Override
    public Result postRecord(AttendanceRecord record, String id) {
        int i = 0;
        try {
            i = studentDao.addRecord(record.getAttend_id(), id, record.getIsAttendance(), record.getSign_in_time());
        } catch (Exception e) {
            e.printStackTrace();
            i = -1;
        }
        studentDao.updateCourseRecord(record.getAttend_id());
        if (i == 1) {
            studentDao.deleteTask(id);
            return Result.success();
        } else return Result.failed();
    }

    @Override
    public Result<Student> updateFaceUrl(String stu_id, String url) {
        int i;
        try {
            i = studentDao.updateFaceUrl(url, stu_id);
        } catch (Exception e) {
            i = -1;
            e.printStackTrace();
        }
        if (i == 1) {
            return Result.success(studentDao.getStudentById(stu_id));
        } else {
            return Result.failed();
        }
    }

    @Override
    public Result addLeave(StudentLeave leave) {
        int i;
        try {
            i = studentDao.addLeave(leave);
        } catch (Exception e) {
            i = -1;
            e.printStackTrace();
        }
        if (i == 1)
            return Result.success();
        else return Result.failed();
    }
}
