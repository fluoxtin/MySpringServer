package com.example.springprojectdemo.userlogin.service.impl;

import com.example.springprojectdemo.userlogin.dao.StudentDao;
import com.example.springprojectdemo.userlogin.dao.TeacherDao;
import com.example.springprojectdemo.userlogin.dao.UserDao;
import com.example.springprojectdemo.userlogin.dataobject.Student;
import com.example.springprojectdemo.userlogin.dataobject.Teacher;
import com.example.springprojectdemo.userlogin.dataobject.User;
import com.example.springprojectdemo.userlogin.model.Result;
import com.example.springprojectdemo.userlogin.service.UserService;
import com.example.springprojectdemo.userlogin.util.ClassExamine;
import com.example.springprojectdemo.util.TokenUtils;
import com.mysql.cj.util.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author fluoxtin created on 2022/4/9
 */
@Component
public class UserServiceImpl<T> implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private TeacherDao teacherDao;

    @Override
    public Result loginOrRegister(User user) {
        Result result = new Result<>();
        User getUser = userDao.getByUserName(user.getUsername());
        if (getUser != null) {
            result.setResultFailed("该用户名已经存在!");
            return result;
        }
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        userDao.add(user);

        String token = TokenUtils.token(user.getUsername(), user.getPassword());
        result.setToken(token);
        if (user.getRole() == 0) {
            Teacher teacher = null;
            try {
                teacher = teacherDao.getTeacherById(user.getUsername());
            } catch (Exception e) {
                e.printStackTrace();
            }
            result.setResultSuccess(
                    "register success",
                    teacher
            );
        } else {
            Student student = null;
            try {
                 student = studentDao.getStudentById(user.getUsername());
//                student = studentDao.getStudentById(user.getUsername()).get(0);
            } catch (Exception e) {
                e.printStackTrace();
            }

            result.setResultSuccess(
                    "register success",
                    student
            );

        }
        return result;
    }

    @Override
    public Result<Student> updateStudent(Student student) {
        Result<Student> result = new Result<>();
        studentDao.addStudent(student);
        result.setResultSuccess("完善信息成功", studentDao.getStudentById(student.getStu_id()));
        return result;
    }

    @Override
    public Result<Teacher> updateTeacher(Teacher teacher) {
        Result<Teacher> result = new Result<>();
        teacherDao.addTeacher(teacher);
        result.setResultSuccess("完善信息成功", teacherDao.getTeacherById(teacher.getTea_id()));
        return result;
    }

    @Override
    public Result<User> update(User user) throws Exception {
        Result<User> result = new Result<>();
        User getUser = userDao.getByUserName(user.getUsername());
        if (getUser == null) {
            result.setResultFailed("用户不存在!");
            return result;
        }
        if (!StringUtils.isNullOrEmpty(user.getPassword())) {
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        }
        // 对象互补 检测字段为空则用数据库对应对象相应字段补上
        ClassExamine.objectOverlap(user, getUser);
        userDao.update(user);
        result.setResultSuccess("修改用户成功", user);
        return result;
    }

    @Override
    public Result<User> isLogin(HttpSession session) {
        Result<User> result = new Result<>();
        return result;
    }
}
