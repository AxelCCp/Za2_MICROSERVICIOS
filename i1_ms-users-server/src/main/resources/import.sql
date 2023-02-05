INSERT INTO `users` (username, password, enabled, name, lastname, email) VALUES('axelccp','1234567',1,'Axel','Astete','axel.astete.o@gmail.com');
INSERT INTO `users` (username, password, enabled, name, lastname, email) VALUES('usuario2','7654321',1,'usuario','two','usuario2@two.com');

INSERT INTO `roles` (name) VALUES ('ROLE_USER');
INSERT INTO `roles` (name) VALUES ('ROLE_ADMIN');

INSERT INTO `users_roles` (user_id, role_id) VALUES (1,1);
INSERT INTO `users_roles` (user_id, role_id) VALUES (2,2);
INSERT INTO `users_roles` (user_id, role_id) VALUES (2,1);