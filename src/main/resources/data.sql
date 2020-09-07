INSERT INTO users (id, name, email, enabled, password, registered) VALUES
(9, 'Regina', 'bla@bla.com', 1, '$2y$10$Q2jpyG3i5TbjHe7tNmZyHuUG0esearK5e1nnN9E67QCDeblaKXMly', '2020-09-04 15:19:40'),
(10, 'Vadim', 'blabla@bla.com', 1, '$2y$10$RbQMub6zXu2O8rrXuoN5wOyF.oPfj8UOCgdjri70dzxrDpmBNHvOK', '2020-09-04 15:21:23'),
(18, 'Luna', 'blablabla@bla.com', 1, '$2y$10$rpSJES7Zjiu/5Dh7WmkLJOCw6GlBGyZZN6exECLl/pvQUOXHEj5pa', '2020-09-06 15:21:23');

INSERT INTO roles (id, name) VALUES
(12, 'ADMIN'),
(13, 'USER');

INSERT INTO user_roles (user_id, roles_id) VALUES
(9, 12),
(9, 13),
(10, 13),
(18, 13);

INSERT INTO restaurants (id, name, user_id) VALUES
(7, 'Okayama', 9),
(8, 'Denny', 9),
(19, 'Barbecue', 9);

INSERT INTO menus (id, date, restaurant_id) VALUES
(5, CURRENT_DATE() - INTERVAL 1 DAY, 7),
(14, CURRENT_DATE(), 7),
(6, CURRENT_DATE(), 8);

INSERT INTO dishes (id, name, price, menu_id) VALUES
(1, 'California Roll', 8, 5),
(2, 'Philadelphia Roll', 10, 5),
(15, 'Alaska Roll', 12, 14),
(16, 'Philadelphia Roll', 15, 14),
(3, 'Chicken Burger', 10, 6),
(4, 'Beef Burger', 15, 6);

INSERT INTO votes (id, date, menu_id, user_id) VALUES
(11, CURRENT_DATE() - INTERVAL 1 DAY, 5, 10),
(17, CURRENT_DATE(), 14, 10);

ALTER sequence HIBERNATE_SEQUENCE restart with 20;
