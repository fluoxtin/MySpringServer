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
            @Result(property = "name", column = "name"),
            @Result(property = "sex", column = "sex"),
            @Result(property = "phone", column = "phone"),
            @Result(property = "email", column = "email"),
            @Result(property = "unit", column = "unit"),
    })
    Teacher getTeacherById(String id);

    @Insert("insert into Teacher (tea_id, name, sex, phone, email, unit) " +
            "values (#{tea_id}, #{name}, #{sex}, #{phone}, #{email}, #{unit})")
    int addTeacher(Teacher teacher);

    @Select("select * from course where tea_id = #{id}")
    @Results({
            @Result(property = "cour_id", column = "cour_id"),
            @Result(property = "cour_name", column = "cour_name"),
            @Result(property = "class_time", column = "class_time")
    })
    List<Course> getCourses(String id);

    @Select("select stu_id from student_course where cour_id = #{cour_id}")
    List<String> getStuIds(String cour_id);

    @Insert("insert into attend_task (attend_id, stu_id, dead_time) " +
            "values (#{attend_id}, #{stu_id}, #{dead_time})")
    boolean addTask(StudentTask task);

    @Select("select * from course_attendance where tea_id = #{tea_id}")
    List<CourseAttendRecord> getCourseAttendRecord(String tea_id);

}
