package com.example.springprojectdemo.dao;

import com.example.springprojectdemo.dataobject.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherDao {
    /**
     *
     * @param id 工号
     * @return teacher
     */
    @Select("select * from teacher where tea_id = #{id}")
    @Results({
            @Result(property = "tea_id", column = "tea_id"),
            @Result(property = "tea_name", column = "tea_name"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "email", column = "email"),
            @Result(property = "unit", column = "unit"),
    })
    Teacher getTeacherById(String id);

    @Insert("insert into teacher (tea_id, tea_name, sex, phone, email, unit) " +
            "values (#{tea_id}, #{tea_name}, #{sex}, #{phone}, #{email}, #{unit})")
    int addTeacher(Teacher teacher);

    @Select("select c.cour_id, c.cour_name, c.class_time, t.tea_name " +
            "from course c, teacher t where t.tea_id = #{id} and c.tea_id = #{id}")
    @Results({
            @Result(property = "cour_id", column = "cour_id"),
            @Result(property = "cour_name", column = "cour_name"),
            @Result(property = "class_time", column = "class_time"),
            @Result(property = "tea_name", column = "tea_name")
    })
    List<Course> getCourses(String id);

    @Select("select stu_id from student_course where cour_id = #{cour_id}")
    List<String> getStuIds(String cour_id);

    @Insert("insert into attend_task (attend_id, stu_id, deadline, latitude, longitude) " +
            "values (#{attend_id}, #{stu_id}, #{deadline}, #{latitude}, #{longitude})")
    boolean addTask(StudentTask task);

    @Select("select ca.attend_id, ca.time, ca.actual_attendance, ca.total_student, c.cour_name" +
            "from course_attendance ca, course c" +
            "where ca.cour_id = c.cour_id and ca.tea_id = #{tea_id}")
    @Results({
            @Result(property = "attend_id", column = "attend_id"),
            @Result(property = "cour_name", column = "cour_name"),
            @Result(property = "time", column = "time"),
            @Result(property = "total_student", column = "total_student"),
            @Result(property = "actual_attendance", column = "actual_attendance")
    })
    List<CourseAttendRecord> getCourseAttendRecord(String tea_id);

    @Insert("insert into course_attendance (attend_id, cour_id, tea_id, total, actual, time) " +
            "values (#{attend_id}, #{cour_name}, #{tea_id}, #{total}, #{actual}, #{time})")
    int addCourseRecord(CourseAttendRecord record);

}
