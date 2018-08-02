#

create database mmsauth;

use mmsauth;

create table user_role(
  role_id int primary key,
  role_name nvarchar(30) not null
);

create table user(
  user_id int primary key auto_increment,
  user_name nvarchar(30) not null,
  password nvarchar(256) not null,
  role_id int,
  email nvarchar(50) not null,
  is_active int,
  first_name nvarchar(30),
  last_name nvarchar(30),
  birth_date nvarchar(30),
  foreign key (role_id) references user_role(role_id)
);

insert into user_role (role_id, role_name) values
  (1, 'ADMIN'),
  (2, 'USER');

insert into user (user_id, first_name, last_name, user_name, password, birth_date, role_id, email, is_active) VALUES
  (1, 'John', 'Malcovich', 'admin', '$2a$10$2q2MUhRhIzaG5U5Pxt8VB.YCqYkqVLMsb9CY3ILQXa7VsTfxocHOW', '21.12.1997', 2, 'admin@test.com',0),
  (2, 'Liana', 'Blackburn', 'user', '$2a$10$2q2MUhRhIzaG5U5Pxt8VB.YCqYkqVLMsb9CY3ILQXa7VsTfxocHOW', '21.12.1997', 2, 'user@test.com',0);
