package com.example.springprojectdemo.userlogin.dao;

import com.example.springprojectdemo.userlogin.dataobject.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentDao {

    @Select("select * from student where stu_id = #{stu_id}")
    @Results({
            @Result(property = "stu_id", column = "stu_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "email", column = "email"),
            @Result(property = "unit", column = "unit"),
            @Result(property = "stu_class", column = "stu_class"),
            @Result(property = "major", column = "major")
    })
    Student getStudentById(String stu_id);

    @Insert("insert into student (stu_id, name, sex, phone, email, unit, stu_class, major) " +
            "values (#{stu_id}, #{name}, #{sex}, #{phone}, #{email}, #{unit}, #{stu_class}, #{major})")
    int addStudent(Student student);
}
