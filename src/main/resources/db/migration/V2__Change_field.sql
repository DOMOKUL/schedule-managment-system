drop table if exists audiences cascade ;

drop table if exists faculties cascade ;

drop table if exists groups cascade ;

drop table if exists lectures cascade ;

drop table if exists lessons cascade ;

drop table if exists subjects cascade ;

drop table if exists teachers cascade ;

drop table if exists students cascade ;

create table audiences
(
    id       bigint not null,
    capacity integer,
    number   integer unique,
    primary key (id)
);

create table faculties
(
    id   bigint not null,
    name varchar(128) unique,
    primary key (id)
);

create table groups
(
    id         bigint not null,
    name       varchar(128) unique,
    faculty_id bigint not null,
    primary key (id)
);

create table lectures
(
    id          bigint not null,
    date        timestamp,
    number      integer,
    audience_id bigint not null,
    group_id    bigint not null,
    lesson_id   bigint not null,
    teacher_id  bigint not null,
    primary key (id)
);

create table lessons
(
    id         bigint not null,
    duration   bigint,
    number     integer,
    start_time time,
    subject_id bigint not null,
    primary key (id)
);

create table students
(
    id            bigint not null,
    course_number integer,
    faculty_id    bigint not null,
    group_id      bigint not null,
    primary key (id)
);

create table subjects
(
    id   bigint not null,
    name varchar(128) unique,
    primary key (id)
);

create table teachers
(
    id         bigint not null,
    faculty_id bigint not null,
    primary key (id)
);