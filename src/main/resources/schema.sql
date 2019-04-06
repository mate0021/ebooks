drop table if exists users;

create table users(id bigint auto_increment, username varchar(255), password varchar(255), enabled boolean);

insert into users(username,password,enabled) values('mate00','$2a$10$sGx1EawJhzFIaYJHLDSs5ewQqzCO6M6jGAZnMcw6RUAsuAwe2CUMK',true);

drop table if exists authorities;

create table authorities(username  varchar(255),authority  varchar(255), UNIQUE(username,authority));
insert into authorities(username,authority) values('mate00','admin');