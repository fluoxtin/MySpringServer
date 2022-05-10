package com.example.springprojectdemo.service.impl;

import com.example.springprojectdemo.dao.StudentDao;
import com.example.springprojectdemo.dao.TeacherDao;
import com.example.springprojectdemo.dao.UserDao;
import com.example.springprojectdemo.dataobject.Student;
import com.example.springprojectdemo.dataobject.Teacher;
import com.example.springprojectdemo.dataobject.User;
import com.example.springprojectdemo.model.Result;
import com.example.springprojectdemo.model.ResultCode;
import com.example.springprojectdemo.service.UserService;
import com.example.springprojectdemo.util.ClassExamine;
import com.example.springprojectdemo.util.TokenUtils;
import com.mysql.cj.util.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author fluoxtin created on 2022/4/9
 */
@Component
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private StudentDao studentDao;

    @Resource
    private TeacherDao teacherDao;

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

        Student student = null;
        try {
             student = studentDao.getStudentById(user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }

    @Override
    public Result<Teacher> loginOrRegisterForT(User user) {
        Result<Teacher> result = new Result<>();
        User getTUser = userDao.getByUserName(user.getUsername());
        if (getTUser != null) {
            if (!getTUser.getPassword().equals(DigestUtils.md5Hex(user.getPassword()))) {
                result.setResultCode(ResultCode.LOGIN_FAILED);
                return result;
            }
        } else {
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
            userDao.add(user);
        }
        Teacher teacher = null;
        try {
            teacher = teacherDao.getTeacherById(user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.success(teacher);
        return result;
    }

    @Override
    public Result<Student> updateStudent(Student student) {
        Result<Student> result = new Result<>();
        studentDao.addStudent(student);
        result.success(studentDao.getStudentById(student.getStu_id()));
        return result;
    }

    @Override
    public Result<Teacher> updateTeacher(Teacher teacher) {
        Result<Teacher> result = new Result<>();
        teacherDao.addTeacher(teacher);
        result.success(teacherDao.getTeacherById(teacher.getTea_id()));
        return result;
    }

    @Override
    public Result<User> update(User user) throws Exception {
        Result<User> result = new Result<>();
        User getUser = userDao.getByUserName(user.getUsername());
        if (getUser == null) {
            result.setResultCode(ResultCode.FAILED_USER_NOT_EXIST);
            return result;
        }
        if (!StringUtils.isNullOrEmpty(user.getPassword())) {
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        }
        // 对象互补 检测字段为空则用数据库对应对象相应字段补上
        ClassExamine.objectOverlap(user, getUser);
        userDao.update(user);
        result.success(user);
        return result;
    }


}
