CREATE SCHEMA IF NOT EXISTS tasklist;

USE tasklist;

CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS tasks
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    title           VARCHAR(255) NOT NULL,
    description     VARCHAR(255),
    status          VARCHAR(255) NOT NULL,
    expiration_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS user_tasks
(
    user_id BIGINT NOT NULL,
    task_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, task_id),
    CONSTRAINT fk_user_tasks_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT fk_user_tasks_tasks FOREIGN KEY (task_id) REFERENCES tasks (id) ON DELETE CASCADE ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id BIGINT       NOT NULL,
    role    VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id, role),
    CONSTRAINT fk_user_roles_users FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE NO ACTION
);
