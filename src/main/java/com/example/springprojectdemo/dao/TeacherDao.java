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

    @Select("select c.cour_id, c.cour_name, c.start, c.end, c.week_day, t.tea_name " +
            "from course c, teacher t where t.tea_id = #{id} and c.tea_id = #{id}")
    @Results({
            @Result(property = "cour_id", column = "cour_id"),
            @Result(property = "cour_name", column = "cour_name"),
            @Result(property = "start", column = "start"),
            @Result(property = "end", column = "end"),
            @Result(property = "week_day", column = "week_day"),
            @Result(property = "tea_name", column = "tea_name")
    })
    List<Course> getCourses(String id);

    @Select("select stu_id from student_course where cour_id = #{cour_id}")
    List<String> getStuIds(String cour_id);

    @Insert("insert into attend_task (attend_id, stu_id, deadline, latitude, longitude) " +
            "values (#{attend_id}, #{stu_id}, #{deadline}, #{latitude}, #{longitude})")
    boolean addTask(StudentTask task);

    @Select("select ca.attend_id, ca.time, ca.actual, ca.total, c.cour_name, ca.tea_id " +
            " from course_attendance ca, course c " +
            " where ca.cour_id = c.cour_id and ca.cour_id in " +
            "(select cour_id from course_attendance where tea_id = #{tea_id})")
    @Results({
            @Result(property = "attend_id", column = "attend_id"),
            @Result(property = "time", column = "time"),
            @Result(property = "actual", column = "actual"),
            @Result(property = "total", column = "total"),
            @Result(property = "cour_name", column = "cour_name"),
            @Result(property = "tea_id", column = "tea_id")
    })
    List<CourseAttendRecord> getCourseAttendRecord(String tea_id);

    @Insert("insert into course_attendance (attend_id, cour_id, tea_id, total, actual, time) " +
            "values (#{attend_id}, #{cour_name}, #{tea_id}, #{total}, #{actual}, #{time})")
    int addCourseRecord(CourseAttendRecord record);

    @Select("select sa.atten_id, sa.stu_id, sa.attendance, sa.sign_in_time, s.name " +
            "from student_attend sa, student s " +
            "where sa.stu_id = s.stu_id and sa.atten_id = #{attend_id}")
    @Results({
            @Result(property = "atten_id", column = "atten_id"),
            @Result(property = "stu_id", column = "stu_id"),
            @Result(property = "attendance", column = "attendance"),
            @Result(property = "sign_in_time", column = "sign_in_time"),
            @Result(property = "stu_name", column = "name"),

    })
    List<StudentRecord> getAllStudentRecords(String attend_id);

    @Select("select * from attend_task where deadline =< #{cur_time}")
    List<StudentTask> getOverdueTask(long cur_time);

    @Delete("delete from attend_task where deadline =< #{cur_time}")
    int deleteAttendTask(long cur_time);

    @Insert("insert into student_attend (atten_id, stu_id, attendance, sign_in_time) " +
            "values (#{atten_id}, #{stu_id}, #{attendance}, #{sign_in_time})")
    int addRecord(@Param("atten_id")String atten_id, @Param("stu_id")String stu_id,
                  @Param("attendance")int isAttend, @Param("sign_in_time")long time);

    @Insert("insert into teacher_task (attend_id, tea_id, deadline, course_name) " +
            "values (#{attend_id}, #{tea_id}, #{deadline}, #{course_name})")
    int addCurrentTask(
            @Param("attend_id")String attend_id,
            @Param("tea_id")String tea_id,
            @Param("deadline")long deadline,
            @Param("course_name")String course_name
    );

    @Delete("delete from teacher_task where deadline =< #{deadline}")
    int deleteOverdueTask(long deadline);

    @Delete("delete from teacher_task where tea_id = #{id}")
    void deleteTaskById(String id);

    @Select("select cour_name from course where cour_id = #{id}")
    String getCourseNameById(String id);

    @Select("select * from teacher_task where tea_id = #{id}")
    @Results({
            @Result(property = "attend_id", column = "attend_id"),
            @Result(property = "tea_id", column = "tea_id"),
            @Result(property = "deadline", column = "deadline"),
            @Result(property = "course_name", column = "course_name"),
    })
    TeacherTask getTaskById(String id);

}
