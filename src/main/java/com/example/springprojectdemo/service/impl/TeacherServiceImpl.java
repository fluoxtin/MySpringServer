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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class TeacherServiceImpl implements TeacherService {

    @Resource
    UserDao userDao;

    @Resource
    TeacherDao teacherDao;

    @Resource
    SqlSessionFactory sqlSessionFactory;

    ScheduledExecutorService service = Executors.newScheduledThreadPool(10);

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
    public Result<AttendTask> postTask(AttendTask task, String tea_id) {

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
            td.addTask(studentTask);
        }

        if (td.getTaskById(tea_id) != null)
            td.deleteTaskById(tea_id);

        td.addCurrentTask(
                task.getAttend_id(),
                tea_id,
                task.getDeadline(),
                td.getCourseNameById(task.getCour_id())
        );

        CourseAttendRecord record = new CourseAttendRecord(
                tea_id,
                task.getAttend_id(),
                task.getCour_id(),
                task.getDeadline() - (5 * 60 * 1000),
                stuIds.size(),
                0
        );
        td.addCourseRecord(record);
        session.commit();

        // 执行定时任务查找过期的任务
        service.schedule(() -> {
                    System.out.println( "current thread" + Thread.currentThread().getName());
                    System.out.println("执行定时任务查找过期的任务");
            SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
            TeacherDao dao = sqlSession.getMapper(TeacherDao.class);
            long now = System.currentTimeMillis();
                    System.out.println("now : " + now);
            List<StudentTask> overdueTasks = dao.getOverdueTask(now);
            System.out.println("overdueTask size : " + overdueTasks.size());
            dao.deleteAttendTask(now);
            dao.deleteOverdueTask(now);
            for (StudentTask overdueTask : overdueTasks) {
                boolean isLeave;
                try {
                    isLeave = dao.getIdIfLeave(overdueTask.getStu_id(), now);
                }catch (Exception e) {
                    isLeave = false;
                }
                if (isLeave)
                    dao.addRecord(overdueTask.getAttend_id(), overdueTask.getStu_id(), 2, now);
                else dao.addRecord(overdueTask.getAttend_id(), overdueTask.getStu_id(), 0, now);
            }
            sqlSession.commit();
        },
        task.getDeadline() - System.currentTimeMillis(),
             TimeUnit.MILLISECONDS
        );


        return Result.success();
    }

    @Override
    public Result<Teacher> getTeacherById(String tea_id) {
        return Result.success(teacherDao.getTeacherById(tea_id));
    }

    @Override
    public Result<List<StudentRecord>> getAllRecords(String attend_id) {

        return Result.success(teacherDao.getAllStudentRecords(attend_id));
    }

    @Override
    public Result<AttendTask> getCurTask(String tea_id) {
        TeacherTask task = teacherDao.getTaskById(tea_id);

        if (task != null) {
            AttendTask attendTask = new AttendTask(
                    task.getAttend_id(),
                    task.getCourse_name(),
                    task.getDeadline()
            );
            return Result.success(attendTask);
        }
        else return Result.failed();
    }
}
