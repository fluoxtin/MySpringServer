package com.example.springprojectdemo.userlogin.service;

import com.example.springprojectdemo.userlogin.dataobject.Student;
import com.example.springprojectdemo.userlogin.dataobject.Teacher;
import com.example.springprojectdemo.userlogin.dataobject.User;
import com.example.springprojectdemo.userlogin.model.Result;

import javax.servlet.http.HttpSession;

/**
 * @author fluoxtin created on 2022/4/9
 */

public interface UserService {

    Result loginOrRegister(User user);

    Result<Student> updateStudent(Student student);

    Result<Teacher> updateTeacher(Teacher teacher);

    Result<User> update(User user) throws Exception;

    Result<User> isLogin(HttpSession session);
}
