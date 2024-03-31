package com.popov.tasklist.repository.impl;

import com.popov.tasklist.domain.exception.ResourceMappingException;
import com.popov.tasklist.domain.user.Role;
import com.popov.tasklist.domain.user.User;
import com.popov.tasklist.repository.DataSourceConfig;
import com.popov.tasklist.repository.UserRepository;
import com.popov.tasklist.repository.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final DataSourceConfig dataSourceConfig;
    private final String FIND_BY_ID = """
            SELECT u.id              as user_id,
                   u.name            as user_name,
                   u.username        as user_username,
                   u.password        as user_password,
                   ur.role           as user_role_role,
                   t.id              as task_id,
                   t.title           as task_title,
                   t.description     as task_description,
                   t.expiration_date as task_expiration_date,
                   t.status          as task_status
            FROM users u
                     LEFT JOIN users_roles ur ON u.id = ur.user_id
                     LEFT JOIN tasklist.users_tasks ut on u.id = ut.user_id
                     LEFT JOIN tasks t on ut.task_id = t.id
            where u.id = ?;
            """;

    private final String FIND_BY_USERNAME = """
            SELECT u.id              as user_id,
                   u.name            as user_name,
                   u.username        as user_username,
                   u.password        as user_password,
                   ur.role           as user_role_role,
                   t.id              as task_id,
                   t.title           as task_title,
                   t.description     as task_description,
                   t.expiration_date as task_expiration_date,
                   t.status          as task_status
            FROM users u
                     LEFT JOIN users_roles ur ON u.id = ur.user_id
                     LEFT JOIN tasklist.users_tasks ut on u.id = ut.user_id
                     LEFT JOIN tasks t on ut.task_id = t.id
            where u.username = ?;
            """;

    private final String UPDATE = """
            UPDATE users
            SET name = ?,
                username = ?,
                password = ?
            WHERE id = ?;
            """;

    private final String CREATE = """
            INSERT INTO users (name, username, password)
            VALUES (?, ?, ?);
            """;

    private final String DELETE = """
            DELETE FROM users
            WHERE id = ?;
            """;

    private final String INSERT_USER_ROLE = """
            INSERT INTO users_roles (user_id, role)
            VALUES (?, ?);
            """;

    private final String IS_TASK_OWNER = """
            SELECT EXISTS(
                SELECT 1
                FROM users_tasks
                WHERE user_id = ? AND task_id = ?
            );
            """;

    @Override
    public Optional<User> findByID(Long id) {
        Connection connection = null;
        try {
            connection = dataSourceConfig.getConnection();
            var statement = connection.prepareStatement(FIND_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1, id);
            try (var resultSet = statement.executeQuery()) {
                return Optional.ofNullable(UserMapper.mapUser(resultSet));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while finding user by id.");
        } finally {
            dataSourceConfig.closeConnection(connection);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Connection connection = null;
        try {
            connection = dataSourceConfig.getConnection();
            var statement = connection.prepareStatement(FIND_BY_USERNAME, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, username);
            try (var resultSet = statement.executeQuery()) {
                return Optional.ofNullable(UserMapper.mapUser(resultSet));
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while finding user by username.");
        } finally {
            dataSourceConfig.closeConnection(connection);
        }

    }

    @Override
    public void update(User user) {
        Connection connection = null;
        try {
            connection = dataSourceConfig.getConnection();
            var statement = connection.prepareStatement(UPDATE);
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while updating user.");
        } finally {
            dataSourceConfig.closeConnection(connection);
        }
    }

    @Override
    public void create(User user) {
        Connection connection = null;
        try {
            connection = dataSourceConfig.getConnection();
            var statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            try (var resultSet = statement.getGeneratedKeys()) {
                if(resultSet.next()){
                    user.setId(resultSet.getLong(1));
                }
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while creating user.");
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
            throw new ResourceMappingException("Exception while deleting user.");
        } finally {
            dataSourceConfig.closeConnection(connection);
        }
    }

    @Override
    public void insertUserRole(Long userId, Role role) {
        Connection connection = null;
        try {
            connection = dataSourceConfig.getConnection();
            var statement = connection.prepareStatement(INSERT_USER_ROLE);
            statement.setLong(1, userId);
            statement.setString(2, role.name());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while inserting user role.");
        } finally {
            dataSourceConfig.closeConnection(connection);
        }
    }

    @Override
    public boolean isTaskOwner(Long userId, Long taskId) {
        Connection connection = null;
        try {
            connection = dataSourceConfig.getConnection();
            var statement = connection.prepareStatement(IS_TASK_OWNER);
            statement.setLong(1, userId);
            statement.setLong(2, taskId);
            try (var resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Exception while checking if user is task owner.");
        } finally {
            dataSourceConfig.closeConnection(connection);
        }
    }
}
