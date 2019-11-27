DELETE FROM users;
DELETE FROM user_roles;
DELETE FROM user_meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO user_meals (user_id, date_time, description, calories) VALUES
(100000, '05-10-2019 09:00:00', 'Завтрак', 800),
(100000, '05-10-2019 14:00:00', 'Обед', 1000),
(100000, '10-10-2019 19:00:00', 'Ужин', 500),
(100001, '08-11-2019 15:00:00', 'Обед', 400),
(100001, '09-11-2019 19:00:00', 'Ужин', 1200);