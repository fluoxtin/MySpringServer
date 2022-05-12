package com.example.springprojectdemo.service.impl;

import com.example.springprojectdemo.dao.StudentDao;
import com.example.springprojectdemo.dao.TeacherDao;
import com.example.springprojectdemo.dao.UserDao;
import com.example.springprojectdemo.dataobject.*;
import com.example.springprojectdemo.model.Result;
import com.example.springprojectdemo.model.ResultCode;
import com.example.springprojectdemo.service.StudentService;
import com.example.springprojectdemo.service.TeacherService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TeacherServiceImpl implements TeacherService {

    @Resource
    UserDao userDao;

    @Resource
    TeacherDao teacherDao;

    @Resource
    StudentDao studentDao;

    @Resource
    SqlSessionFactory sqlSessionFactory;

    @Override
    public Result<Teacher> loginOrRegisterForT(User user) {
        Result<Teacher> result = new Result<>();
        User getTUser = userDao.getByUserName(user.getUsername());
        if (getTUser != null) {
            if (!getTUser.getPassword().equals(DigestUtils.md5Hex(user.getPassword()))) {
                result.setResultCode(ResultCode.LOGIN_FAILED);
                return result;
            }
        } else {
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
            userDao.add(user);
        }
        Teacher teacher = teacherDao.getTeacherById(user.getUsername());

        result = Result.success(teacher);
        return result;
    }

    @Override
    public Result<Teacher> updateTeacher(Teacher teacher) {
        Result<Teacher> result;
        teacherDao.addTeacher(teacher);
        result = Result.success(teacherDao.getTeacherById(teacher.getTea_id()));
        return result;
    }

    @Override
    public Result<List<Course>> getCourse(String tea_id) {
        Result<List<Course>> result;
        List<Course> courses = teacherDao.getCourses(tea_id);
        result = Result.success(courses);
        return result;
    }

    /**
     *
     * @param tea_id : teacher id
     * @return list of attendance task
     */
    @Override
    public Result<List<AttendTask>> getAttendTaskRecord(String tea_id) {
        teacherDao.getCourseAttendRecord(tea_id);
        return null;
    }

    @Override
    public Result postTask(AttendTask task) {

        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);
        TeacherDao td = session.getMapper(TeacherDao.class);
        List<String> stuIds = td.getStuIds(task.getCour_id());
        StudentTask studentTask;
        for (String id : stuIds) {
            studentTask = new StudentTask(task.getAttend_id(), id, task.getDead_time());
//            td.addTask(task, id);
            td.addTask(studentTask);
        }
        session.commit();

        return Result.success();
    }
}
