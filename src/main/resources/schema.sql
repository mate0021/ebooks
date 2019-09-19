drop table if exists shop;
CREATE TABLE shop (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255)  DEFAULT NULL,
  url varchar(255)  DEFAULT NULL,
  PRIMARY KEY (id)
);

drop table if exists theme;
CREATE TABLE theme (
  id int(11) NOT NULL AUTO_INCREMENT,
  theme varchar(255)  DEFAULT NULL,
  PRIMARY KEY (id)
);

drop table if exists reader;
CREATE TABLE reader (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255)  DEFAULT NULL,
  PRIMARY KEY (id)
);

drop table if exists book;
CREATE TABLE book (
  id int(11) NOT NULL AUTO_INCREMENT,
  author varchar(255) DEFAULT NULL,
  locations int(11) NOT NULL,
  title varchar(255) DEFAULT NULL,
  theme_id int(11) DEFAULT NULL,
  PRIMARY KEY (id)
);

drop table if exists reading_progress;
CREATE TABLE reading_progress (
  reading_end date DEFAULT NULL,
  reading_start date NOT NULL,
  key_book int(11) NOT NULL,
  key_reader int(11) NOT NULL,
  PRIMARY KEY (key_book,key_reader)
);

drop table if exists shopping;
CREATE TABLE shopping (
  buy_date date DEFAULT NULL,
  price decimal(19,2) DEFAULT NULL,
  key_book int(11) NOT NULL,
  key_reader int(11) NOT NULL,
  key_shop int(11) NOT NULL,
  PRIMARY KEY (key_book,key_reader,key_shop)
);

drop table if exists role;
create table role (
    id int(10) not null auto_increment,
    role_name varchar(100) not null,
    primary key (id)
);

drop table if exists users;
create table users (
    id int(10) not null auto_increment,
    username varchar(100) not null,
    password varchar(100) not null,
    enabled tinyint(1) not null,
    primary key (id)
);

drop table if exists users_roles;
create table users_roles (
    users_id int(10) not null,
    roles_id int(10) not null
);
--drop table if exists users;
--
--create table user(id bigint auto_increment, username varchar(255), password varchar(255), enabled boolean);
--
--insert into user(id, username, password, enabled) values(1, 'mate00', '$2a$10$sGx1EawJhzFIaYJHLDSs5ewQqzCO6M6jGAZnMcw6RUAsuAwe2CUMK', true);
--
--drop table if exists authorities;
--
--create table authorities(username  varchar(255),authority  varchar(255), UNIQUE(username,authority));
--insert into authorities(username,authority) values('mate00','admin');
