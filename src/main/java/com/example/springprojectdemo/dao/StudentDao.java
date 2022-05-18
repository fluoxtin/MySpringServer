package com.example.springprojectdemo.dao;

import com.example.springprojectdemo.dataobject.*;
import com.mysql.cj.protocol.x.XProtocolRow;
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

    @Select({"select",
            " c.cour_id, c.cour_name, c.class_time, t.tea_name ",
            "from course c, teacher t ",
            "where c.tea_id = t.tea_id and c.cour_id in ",
            "(select cour_id from student_course where stu_id = #{stu_id})"})
    @Results({
            @Result(property = "cour_id", column = "cour_id"),
            @Result(property = "cour_name", column = "cour_name"),
            @Result(property = "class_time", column = "class_time"),
            @Result(property = "tea_name", column = "tea_name")
    })
    List<Course> getCourses(String stu_id);

    @Select("select ca.attend_id, c.cour_name, sa.attendance,t.tea_name, sa.sign_in_time\n" +
            "from student_attend sa, course_attendance ca, course c, teacher t where c.cour_id = ca.cour_id and attend_id\n" +
            " in (select attend_id from course_attendance where stu_id = #{stu_id}')")
    @Results({
            @Result(property = "attend_id", column = "attend_id"),
            @Result(property = "cour_name", column = "cour_name"),
            @Result(property = "attendance", column = "attendance"),
            @Result(property = "sign_in_time", column = "sign_in_time"),
            @Result(property = "tea_name", column = "tea_name"),
    })
    List<AttendanceRecord> getAttendanceRecord(String stu_id);

    @Select("select * from attend_task where stu_id = #{stu_id}")
    StudentTask getTask(String stu_id);

    @Delete("delete from attend_task where stu_id = #{stu_id}")
    void deleteTask(String stu_id);

    @Select("select cour_id from course_attendance where attend_id = #{attend_id}")
    String getCourIdFromCR(String attend_id);

}
