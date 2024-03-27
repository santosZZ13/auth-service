create schema `gateway`;
drop table if exists user;
create table user
(
    id         int auto_increment primary key,
    username   varchar(255) not null,
    password   varchar(255) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp
);


insert into user (username, password) values ('nam1', '123');
insert into user (username, password) values ('nam2', '123');
insert into user (username, password) values ('nam3', '123');