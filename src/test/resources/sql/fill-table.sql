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
    add column number   integer,
    add column capacity integer;

alter table subjects
    add column id   bigserial not null primary key,
    add column name varchar(128);

alter table faculties
    add column id   bigserial not null primary key,
    ADD COLUMN name varchar(128);

alter table lessons
    add column id         bigserial not null primary key,
    add column number     integer,
    add column start_time time,
    add column duration   bigint,
    add column subject_id bigint references subjects (id) on delete cascade;

alter table groups
    add column id         bigserial not null primary key,
    add column name       varchar(128),
    add column faculty_id bigint references faculties (id) ON DELETE CASCADE;

alter table students
    add column id            bigserial not null primary key,
    add column first_name    varchar(128),
    add column last_name     varchar(128),
    add column middle_name   varchar(128),
    add column course_number integer,
    add column group_id      bigint references groups (id) on delete cascade,
    add column faculty_id    bigint references faculties (id) on delete cascade;

alter table teachers
    add column id          bigserial not null primary key,
    add column first_name  varchar(128),
    add column last_name   varchar(128),
    add column middle_name varchar(128),
    add column faculty_id  bigint references faculties (id) ON DELETE CASCADE;

alter table lectures
    add column id          bigserial not null primary key,
    add column number      integer,
    add column date        timestamp,
    add column audience_id bigint references audiences (id) on delete cascade,
    add column group_id    bigint references groups (id) on delete cascade,
    add column lesson_id   bigint references lessons (id) on delete cascade,
    add column teacher_id  bigint references teachers (id) on delete cascade;

insert into audiences
values (1, 1, 1);

insert into faculties (id, name)
values (1, 'IKBSP');

insert into subjects (id, name)
values (1, 'math');

insert into lessons (id, duration, number, start_time, subject_id)
values (1, 5400000000000, 1, '13:00:00', 1);

insert into groups(id, name, faculty_id)
values (1, 'BSBO-04-20', 1);

insert into students (id, first_name, last_name, middle_name, course_number, faculty_id, group_id)
values (1, 'Ivan', 'Ivanov', 'Ivanovich', 1, 1, 1);

insert into teachers (id, first_name, last_name, middle_name, faculty_id)
values (1, 'Alex', 'Alexandrov', 'Alexandrovich', 1);

insert into lectures (id, date, number, audience_id, group_id, lesson_id, teacher_id)
values (1, '1988-09-29', 1, 1, 1, 1, 1);


