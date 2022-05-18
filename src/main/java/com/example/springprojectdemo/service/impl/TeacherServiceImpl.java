package com.example.springprojectdemo.service.impl;

import com.example.springprojectdemo.dao.TeacherDao;
import com.example.springprojectdemo.dao.UserDao;
import com.example.springprojectdemo.dataobject.*;
import com.example.springprojectdemo.model.Result;
import com.example.springprojectdemo.model.ResultCode;
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
        teacherDao.addTeacher(teacher);
        return Result.success(teacherDao.getTeacherById(teacher.getTea_id()));
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
    public Result<List<CourseAttendRecord>> getRecord(String tea_id) {

        return Result.success(teacherDao.getCourseAttendRecord(tea_id));
    }

    @Override
    public Result postTask(AttendTask task, String tea_id) {

        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);
        TeacherDao td = session.getMapper(TeacherDao.class);
        List<String> stuIds = td.getStuIds(task.getCour_id());
        StudentTask studentTask;
        for (String id : stuIds) {
            studentTask = new StudentTask(
                    task.getAttend_id(),
                    id,
                    task.getDeadline(),
                    task.getLocation().getLatitude(),
                    task.getLocation().getLongitude()
            );
//            td.addTask(task, id);
            td.addTask(studentTask);
        }
        CourseAttendRecord record = new CourseAttendRecord(
                tea_id,
                task.getAttend_id(),
                task.getCour_id(),
                task.getDeadline() - (8 * 60 * 1000),
                stuIds.size(),
                0
        );
        td.addCourseRecord(record);
        session.commit();

        return Result.success();
    }

    @Override
    public Result<Teacher> getTeacherById(String tea_id) {
        return Result.success(teacherDao.getTeacherById(tea_id));
    }
}
