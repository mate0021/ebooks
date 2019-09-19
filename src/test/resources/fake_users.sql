drop table if exists role;

create table role (
    id bigint auto_increment,
    role_name varchar(255),
    PRIMARY KEY (`id`)
);

insert into role values (1, 'admin');
insert into role values (2, 'reader');

drop table if exists users_roles;

create table users_roles (
    users_id bigint,
    roles_id bigint
);

drop table if exists users;

create table users (
    id bigint auto_increment,
    username varchar(255),
    password varchar(255),
    enabled boolean,
    PRIMARY KEY (`id`)
);

insert into users (username, password, enabled) values ('test_reader', '$2a$10$sGx1EawJhzFIaYJHLDSs5ewQqzCO6M6jGAZnMcw6RUAsuAwe2CUMK', true);

insert into users_roles (users_id, roles_id) values (1, 1);
insert into users_roles (users_id, roles_id) values (1, 2);
