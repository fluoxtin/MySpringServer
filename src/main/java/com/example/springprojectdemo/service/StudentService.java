package com.example.springprojectdemo.service;

import com.example.springprojectdemo.dataobject.*;
import com.example.springprojectdemo.model.Result;

import java.util.List;

public interface StudentService {

    Result<Student> loginOrRegisterForS(User user);

    Result<Student> updateStudent(Student student);

    Result<List<Course>> getCourse(String stu_id);

    Result<List<AttendanceRecord>> getAttendanceRecord(String stu_id);

    Result<AttendTask> getAttendTask(String stu_id);

    Result<Student> getInfoById(String stu_id);

    Result postRecord(AttendanceRecord record, String id);

    Result<Student> updateFaceUrl(String stu_id, String url);

    Result addLeave(StudentLeave leave);

}
