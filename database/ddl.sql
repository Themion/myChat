drop database myChat;

create database myChat;
use myChat;

create table member (
    id          bigint          not null auto_increment,
    username    varchar(20)     unique not null,
    password    varchar(255)    not null,
    primary key (id)
);

create table chatroom (
    id          bigint          not null auto_increment,
    title       varchar(100)    unique not null,
    population  bigint          not null default 0,
    primary key (id)
);
