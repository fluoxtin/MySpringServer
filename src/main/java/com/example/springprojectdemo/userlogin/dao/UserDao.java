package com.example.springprojectdemo.userlogin.dao;

import com.example.springprojectdemo.userlogin.dataobject.User;
import org.apache.ibatis.annotations.*;

/**
 * @author fluoxtin created on 2022/4/9
 */
@Mapper
public interface UserDao {

    @Insert("insert into user (username, password, role) values (#{username}, #{password}, #{role})")
    int add(User user);

    @Update("update user set password = #{password} where id = #{id}")
    int update(User user);

    @Select("select * from user where id = #{id}")
    @Results({
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password")
    })
    User getById(Integer id);

    @Select("select * from user where username = #{username}")
    @Results({
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password")
    })
    User getByUserName(String username);

}
