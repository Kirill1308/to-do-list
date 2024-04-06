package com.popov.tasklist.repository.mappers;

import com.popov.tasklist.domain.task.Status;
import com.popov.tasklist.domain.task.Task;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TaskRowMapper {

    @SneakyThrows
    public static Task mapRow(ResultSet resultSet) {
        if (resultSet.next()) {
            Task task = new Task();
            task.setId(resultSet.getLong("task_id"));
            task.setTitle(resultSet.getString("task_title"));
            task.setDescription(resultSet.getString("task_description"));
            task.setStatus(Status.valueOf(resultSet.getString("task_status")));
            Timestamp expirationDate = resultSet.getTimestamp("task_expiration_date");
            if (expirationDate != null) {
                task.setExpirationDate(expirationDate.toLocalDateTime());
            }
            return task;
        }

        return null;
    }

    @SneakyThrows
    public static List<Task> mapRows(ResultSet resultSet) {
        List<Task> tasks = new ArrayList<>();

        while (resultSet.next()) {
            Task task = new Task();
            task.setId(resultSet.getLong("task_id"));
            if (!resultSet.wasNull()) {
                task.setTitle(resultSet.getString("task_title"));
                task.setDescription(resultSet.getString("task_description"));
                task.setStatus(Status.valueOf(resultSet.getString("task_status")));
                Timestamp expirationDate = resultSet.getTimestamp("task_expiration_date");
                if (expirationDate != null) {
                    task.setExpirationDate(expirationDate.toLocalDateTime());
                }
                tasks.add(task);
            }
        }

        return tasks;
    }

    // https://github.com/NeilAlishev/SpringCourse/trunk/FirstRestApp
}
