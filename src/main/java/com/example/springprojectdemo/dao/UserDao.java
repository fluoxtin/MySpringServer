package com.example.springprojectdemo.dao;

import com.example.springprojectdemo.dataobject.User;
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


    @Select("select * from user where username = #{username}")
    @Results({
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "role", column = "role")
    })
    User getByUserName(String username);

    @Delete("delete from user where username = #{username}")
    int deleteByUsername(String username);

}
