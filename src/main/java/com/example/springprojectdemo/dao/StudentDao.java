package com.example.springprojectdemo.dao;

import com.example.springprojectdemo.dataobject.*;
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
            @Result(property = "major", column = "major"),
            @Result(property = "face_url", column = "face_url")
    })
    Student getStudentById(String stu_id);

    @Insert("insert into student (stu_id, name, sex, phone, email, unit, stu_class, major) " +
            "values (#{stu_id}, #{name}, #{sex}, #{phone}, #{email}, #{unit}, #{stu_class}, #{major})")
    int addStudent(Student student);

    @Select({"select",
            " c.cour_id, c.cour_name, c.start, c.end,c.week_day, t.tea_name ",
            "from course c, teacher t ",
            "where c.tea_id = t.tea_id and c.cour_id in ",
            "(select cour_id from student_course where stu_id = #{stu_id})"})
    @Results({
            @Result(property = "cour_id", column = "cour_id"),
            @Result(property = "cour_name", column = "cour_name"),
            @Result(property = "start", column = "start"),
            @Result(property = "end", column = "end"),
            @Result(property = "week_day", column = "week_day"),
            @Result(property = "tea_name", column = "tea_name")
    })
    List<Course> getCourses(String stu_id);

    @Select("select sa.atten_id, c.cour_name, sa.attendance, sa.sign_in_time " +
            "from student_attend sa, course_attendance ca, course c " +
            "where c.cour_id = ca.cour_id and ca.attend_id = sa.atten_id and sa.stu_id = #{stu_id} and sa.atten_id " +
            " in (select attend_id from student_attend where stu_id = #{stu_id})")
    @Results({
            @Result(property = "attend_id", column = "attend_id"),
            @Result(property = "cour_name", column = "cour_name"),
            @Result(property = "isAttendance", column = "attendance"),
            @Result(property = "sign_in_time", column = "sign_in_time")
    })
    List<AttendanceRecord> getAttendanceRecord(String stu_id);

    @Select("select * from attend_task where stu_id = #{stu_id}")
    StudentTask getTask(String stu_id);

    @Delete("delete from attend_task where stu_id = #{stu_id}")
    void deleteTask(String stu_id);

    @Select("select cour_id from course_attendance where attend_id = #{attend_id}")
    String getCourIdFromCR(String attend_id);

    @Insert("insert into student_attend (atten_id, stu_id, attendance, sign_in_time) " +
            "values (#{atten_id}, #{stu_id}, #{attendance}, #{sign_in_time})")
    int addRecord(@Param("atten_id")String atten_id, @Param("stu_id")String stu_id,
                  @Param("attendance")int isAttend, @Param("sign_in_time")long time);


    @Update("update course_attendance set actual = actual + 1 where attend_id = #{attend_id}")
    int updateCourseRecord(String attend_id);

    @Update("update student set face_url = #{face_url} where stu_id = #{id}")
    int updateFaceUrl(@Param("face_url") String face_url, @Param("id") String id);

    @Insert("insert into student_leave (stu_id, start, end, msg) " +
            "values (#{stu_id}, #{start}, #{end}, #{msg})")
    int addLeave(StudentLeave leave);

}
