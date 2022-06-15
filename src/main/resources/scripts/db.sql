CREATE TABLE CUSTOM_USER (
	ID serial PRIMARY KEY,
	EMAIL VARCHAR(100) UNIQUE NOT NULL,
	PWD VARCHAR(100) NOT NULL,
	ROLE_NAME VARCHAR(20)
);

-- use https://bcrypt-generator.com/, round 12, password text: 123
INSERT INTO CUSTOM_USER (email, pwd, role_name) VALUES
('duyen@gmail.com', '$2a$12$u3Q8r8XvpQddTHV50rbcKe6VhWm1ItR98uYe2nwF4p80qGLlUG.j6', 'user'),
('duyen_2@gmail.com', '$2a$12$u3Q8r8XvpQddTHV50rbcKe6VhWm1ItR98uYe2nwF4p80qGLlUG.j6', 'admin');


-- Create authority table
CREATE TABLE AUTHORITY (
id serial PRIMARY KEY,
user_name VARCHAR(50) NOT NULL,
authority VARCHAR(50) NOT NULL,
CONSTRAINT fk_authorities_users FOREIGN KEY(user_name) REFERENCES custom_user(email)
);

INSERT INTO AUTHORITY (user_name, authority) VALUES
('duyen@gmail.com', 'READ'),
('duyen_2@gmail.com', 'READ'),
('duyen_2@gmail.com', 'WRITE');

INSERT INTO AUTHORITY (user_name, authority) VALUES
('duyen@gmail.com', 'ROLE_USER'),
('duyen_2@gmail.com', 'ROLE_USER'),
('duyen_2@gmail.com', 'ROLE_ADMIN');


