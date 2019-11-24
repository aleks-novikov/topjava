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
  (100000, date('05-10-2019'), 'Завтрак', 800),
  (100000, date('15-10-2019'), 'Обед', 1000),
  (100000, date('05-10-2019'), 'Ужин', 500),
  (100001, now(), 'Обед', 400),
  (100001, now(), 'Ужин', 1200);