package com.example.springprojectdemo.userlogin.dao;

import com.example.springprojectdemo.userlogin.dataobject.Student;
import com.example.springprojectdemo.userlogin.dataobject.Teacher;
import lombok.Setter;
import org.apache.ibatis.annotations.*;

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
}
