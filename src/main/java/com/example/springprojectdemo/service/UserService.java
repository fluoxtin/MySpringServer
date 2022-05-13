package com.example.springprojectdemo.service;

import com.example.springprojectdemo.dataobject.Student;
import com.example.springprojectdemo.dataobject.Teacher;
import com.example.springprojectdemo.dataobject.User;
import com.example.springprojectdemo.model.Result;

import javax.servlet.http.HttpSession;

/**
 * @author fluoxtin created on 2022/4/9
 */

public interface UserService {

    Result<Student> loginOrRegisterForS(User user);

    Result<Teacher> loginOrRegisterForT(User user);

    Result<Student> updateStudent(Student student);

    Result<Teacher> updateTeacher(Teacher teacher);

    Result<User> update(User user) throws Exception;

    Result<User> getUserById(String id);

}
