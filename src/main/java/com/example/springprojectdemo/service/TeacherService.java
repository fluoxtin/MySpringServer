package com.example.springprojectdemo.service;


import com.example.springprojectdemo.dataobject.AttendTask;
import com.example.springprojectdemo.dataobject.Course;
import com.example.springprojectdemo.dataobject.Teacher;
import com.example.springprojectdemo.dataobject.User;
import com.example.springprojectdemo.model.Result;

import java.util.List;

public interface TeacherService {

    Result<Teacher> loginOrRegisterForT(User user);

    Result<Teacher> updateTeacher(Teacher teacher);

    Result<List<Course>> getCourse(String tea_id);

    Result<List<AttendTask>> getAttendTaskRecord(String tea_id);

    Result postTask(AttendTask task);

    Result<Teacher> getTeacherById(String tea_id);
}
