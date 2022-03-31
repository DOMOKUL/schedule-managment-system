drop table if exists audiences cascade;

drop table if exists faculties cascade;

drop table if exists groups cascade;

drop table if exists lectures cascade;

drop table if exists lessons cascade;

drop table if exists students cascade;

drop table if exists subjects cascade;

drop table if exists teachers cascade;

create table schedule
(
);

create table university
(
);

create table audiences
(
);

create table faculties
(
);

create table departments
(
);

create table lectures
(
);

create table groups
(
);

create table students
(
);

create table lessons
(
);

create table subjects
(
);

create table teachers
(
);

alter table audiences
    add column id       bigserial not null primary key,
    add column number   integer unique,
    add column capacity integer;

alter table subjects
    add column id   bigserial not null primary key,
    add column name varchar(128) unique;

alter table faculties
    add column id   bigserial not null primary key,
    ADD COLUMN name varchar(128) unique;

alter table lessons
    add column id         bigserial not null primary key,
    add column number     integer,
    add column start_time time,
    add column duration   bigint,
    add column subject_id bigint references subjects (id) on
        delete
        cascade;

alter table groups
    add column id         bigserial not null primary key,
    add column name       varchar(128) unique,
    add column faculty_id bigint references faculties (id) ON
        DELETE
        CASCADE;

alter table students
    add column id            bigserial not null primary key,
    add column first_name    varchar(128),
    add column last_name     varchar(128),
    add column middle_name   varchar(128),
    add column course_number integer,
    add column group_id      bigint references groups (id) on
        delete
        cascade;

alter table teachers
    add column id          bigserial not null primary key,
    add column first_name  varchar(128),
    add column last_name   varchar(128),
    add column middle_name varchar(128),
    add column faculty_id  bigint references faculties (id) ON
        DELETE
        CASCADE;

alter table lectures
    add column id          bigserial not null primary key,
    add column number      integer,
    add column date        timestamp,
    add column audience_id bigint references audiences (id) on
        delete
        cascade,
    add column group_id    bigint references groups (id) on delete
        cascade,
    add column lesson_id   bigint references lessons (id) on delete
        cascade,
    add column teacher_id  bigint references teachers (id) on delete
        cascade;

insert into audiences(id, number, capacity)
values (10, 10, 10);

insert into faculties (id, name)
values (10, 'IKBSP');

insert into subjects (id, name)
values (10, 'math');

insert into lessons (id, duration, number, start_time, subject_id)
values (10, 5400000000000, 10, '13:00:00', 10);

insert into groups(id, name, faculty_id)
values (10, 'BSBO-04-20', 10);

insert into students (id, first_name, last_name, middle_name, course_number, group_id)
values (10, 'Ivan', 'Ivanov', 'Ivanovich', 10, 10);

insert into teachers (id, first_name, last_name, middle_name, faculty_id)
values (10, 'Alex', 'Alexandrov', 'Alexandrovich', 10);

insert into lectures (id, date, number, audience_id, group_id, lesson_id, teacher_id)
values (10, '1988-09-29', 10, 10, 10, 10, 10);


