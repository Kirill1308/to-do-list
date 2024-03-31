INSERT INTO users (name, username, password)
VALUES ('John Doe', 'johndoe@gmail.com', '$2a$10$KT6SikWV8tM4EnOU9QXbju9uqY7YyJkzcCDaVt1QO8DtOuZDVyml.'),
       ('Mike Smith', 'mikesmith@gmail.com', '$2a$10$KT6SikWV8tM4EnOU9QXbju9uqY7YyJkzcCDaVt1QO8DtOuZDVyml.');

INSERT INTO tasks (title, description, status, expiration_date)
VALUES ('Buy groceries', NULL, 'TODO', '2020-12-31 12:00:00'),
       ('Pay bills', 'Pay electricity, water, and internet bills', 'IN_PROGRESS', '2020-12-15 00:00:00'),
       ('Call mom', NULL, 'DONE', '2020-12-20 00:00:00');

INSERT INTO user_tasks (task_id, user_id)
VALUES (1, 1),
       (2, 1),
       (3, 2);

INSERT INTO user_roles (user_id, role)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');

truncate table user_roles;

