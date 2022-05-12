package com.example.springprojectdemo.dao;

import com.example.springprojectdemo.dataobject.AttendTask;
import com.example.springprojectdemo.dataobject.AttendanceRecord;
import com.example.springprojectdemo.dataobject.Course;
import com.example.springprojectdemo.dataobject.Student;
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
            " c.cour_id, c.cour_name, c.class_time ",
            "from course c ",
            "where cour_id in ",
            "(select cour_id from student_course where stu_id = #{stu_id})"})
    @Results({
            @Result(property = "cour_id", column = "cour_id"),
            @Result(property = "cour_name", column = "cour_name"),
            @Result(property = "class_time", column = "class_time")
    })
    List<Course> getCourses(String stu_id);

    @Select("select ca.attend_id, c.cour_name, c.class_time, sa.attendance\n" +
            "from student_attend sa, course_attendance ca, course c where c.cour_id = ca.cour_id and attend_id\n" +
            " in (select attend_id from course_attendance where stu_id = #{stu_id}')")
    @Results({
            @Result(property = "attend_id", column = "attend_id"),
            @Result(property = "cour_name", column = "cour_name"),
            @Result(property = "attendance", column = "attendance"),
            @Result(property = "sign_in_time", column = "sign_in_time")
    })
    List<AttendanceRecord> getAttendanceRecord(String stu_id);

    @Select("select attend_id, dead_time from attend_task where stu_id = #{stu_id}")
    @Results({
            @Result(property = "attend_id", column = "attend_id"),
            @Result(property = "dead_time", column = "dead_time")
    })
    AttendTask getTask(String stu_id);

    @Delete("delete from attend_task where stu_id = #{stu_id}")
    void deleteTask(String stu_id);

}
