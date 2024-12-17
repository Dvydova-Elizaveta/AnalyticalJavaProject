package db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import models.Course;
import models.TaskEntity;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


public class DbRepository {
    private final String URL = "jdbc:mysql://localhost:3306/Taskdb?user=root&password=";
    private String password;

    private ConnectionSource connectionSource = null;

    private Dao<TaskEntity, String> taskDao = null;

    public void connect() throws SQLException {
        if (password == null) {
            Scanner in = new Scanner(System.in);
            System.out.print("Input a password: ");
            password = in.nextLine();
        }
        connectionSource = new JdbcConnectionSource(URL + password);
        taskDao = DaoManager.createDao(connectionSource, TaskEntity.class);
    }

    public void createTable() throws SQLException {
        TableUtils.createTable(connectionSource, TaskEntity.class);
    }

    public void saveTasks(Course course) throws SQLException {
        var courseName = course.getName();
        for (var task: course.getTasks()) {
            taskDao.create(new TaskEntity(task.getName(), courseName, task.getTape(),
                    (float) task.getAverageScore() / task.getMaxScore(), task.getCountComments()));
        }
    }

    public List<TaskEntity> getTasks() throws SQLException {
        return taskDao.queryForAll();
    }

    public List<TaskEntity> getTasksByTape(String tape) throws SQLException {
        return taskDao.queryBuilder()
                .where()
                .eq(TaskEntity.TAPE_COLUMN, tape)
                .query();
    }

    public List<TaskEntity> getTasksByCourseAndTape(String course, String tape) throws SQLException {
        return taskDao.queryBuilder()
                .where()
                .eq(TaskEntity.TAPE_COLUMN, tape)
                .eq(TaskEntity.COURSE_COLUMN, course)
                .query();
    }

    public void clouse() throws Exception {
        connectionSource.close();
    }
}
