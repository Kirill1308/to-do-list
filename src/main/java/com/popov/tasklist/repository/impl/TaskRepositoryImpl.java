package com.popov.tasklist.repository.impl;

import com.popov.tasklist.domain.exception.ResourceMappingException;
import com.popov.tasklist.domain.task.Task;
import com.popov.tasklist.repository.DataSourceConfig;
import com.popov.tasklist.repository.TaskRepository;
import com.popov.tasklist.repository.mappers.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final DataSourceConfig dataSourceConfig;
    private final String FIND_BY_ID = """
            select t.id              as task_id,
                   t.title           as task_title,
                   t.description     as task_description,
                   t.expiration_date as task_expiration_date,
                   t.status          as task_status
            from tasks t
            where id = ?""";

    private final String FIND_ALL_BY_USER_ID = """
            select t.id              as task_id,
                   t.title           as task_title,
                   t.description     as task_description,
                   t.expiration_date as task_expiration_date,
                   t.status          as task_status
            from tasks t
                     join users_tasks ut on t.id = ut.task_id
            where user_id = ?""";

    private final String ASSIGN = "INSERT INTO users_tasks (task_id, user_id) VALUES (?, ?)";

    private final String CREATE = """
            INSERT INTO tasks (title, description, expiration_date, status)
            VALUES (?, ?, ?, ?)""";

    private final String UPDATE = """
            UPDATE tasks
            SET title = ?,
                description = ?,
                expiration_date = ?,
                status = ?
            WHERE id = ?""";

    private final String DELETE = """
            DELETE FROM tasks
            WHERE id = ?""";

    @Override
    public Optional<Task> findByID(Long id) {
        Connection connection = null;
        try {
            connection = dataSourceConfig.getConnection();
            var statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            try (var resultSet = statement.executeQuery()) {
                return Optional.ofNullable(TaskRowMapper.mapRow(resultSet));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Failed to find task by id");
        } finally {
            dataSourceConfig.closeConnection(connection);
        }
    }

    @Override
    public List<Task> findAllByUserID(Long userId) {
        Connection connection = null;
        try {
            connection = dataSourceConfig.getConnection();
            var statement = connection.prepareStatement(FIND_ALL_BY_USER_ID);
            statement.setLong(1, userId);
            try (var resultSet = statement.executeQuery()) {
                return TaskRowMapper.mapRows(resultSet);
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Failed to find tasks by user id");
        } finally {
            dataSourceConfig.closeConnection(connection);
        }
    }

    @Override
    public void assignToUserByID(Long taskId, Long userId) {
        Connection connection = null;
        try {
            connection = dataSourceConfig.getConnection();
            var statement = connection.prepareStatement(ASSIGN);
            statement.setLong(1, taskId);
            statement.setLong(2, userId);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Failed to assign task to user by id");
        } finally {
            dataSourceConfig.closeConnection(connection);
        }
    }

    @Override
    public void update(Task task) {
        Connection connection = null;
        try {
            connection = dataSourceConfig.getConnection();
            var statement = connection.prepareStatement(UPDATE);
            statement.setString(1, task.getTitle());
            if (task.getDescription() != null)
                statement.setString(2, task.getDescription());
            else
                statement.setNull(2, java.sql.Types.VARCHAR);

            if (task.getExpirationDate() != null)
                statement.setTimestamp(3, java.sql.Timestamp.valueOf(task.getExpirationDate()));
            else
                statement.setNull(3, java.sql.Types.TIMESTAMP);

            statement.setString(4, task.getStatus().name());
            statement.setLong(5, task.getId());

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error updating task");
        } finally {
            dataSourceConfig.closeConnection(connection);
        }
    }

    @Override
    public void create(Task task) {
        Connection connection = null;
        try {
            connection = dataSourceConfig.getConnection();
            var statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, task.getTitle());
            if (task.getDescription() != null)
                statement.setString(2, task.getDescription());
            else
                statement.setNull(2, java.sql.Types.VARCHAR);

            if (task.getExpirationDate() != null)
                statement.setTimestamp(3, java.sql.Timestamp.valueOf(task.getExpirationDate()));
            else
                statement.setNull(3, java.sql.Types.TIMESTAMP);

            statement.setString(4, task.getStatus().name());

            statement.executeUpdate();
            try (var rs = statement.getGeneratedKeys()) {
                rs.next();
                task.setId(rs.getLong(1));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error creating task");
        } finally {
            dataSourceConfig.closeConnection(connection);
        }
    }

    @Override
    public void delete(Long id) {
        Connection connection = null;
        try {
            connection = dataSourceConfig.getConnection();
            var statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error deleting task");
        } finally {
            dataSourceConfig.closeConnection(connection);
        }
    }
}
