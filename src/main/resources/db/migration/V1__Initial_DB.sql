create sequence audience_sequence start 1 increment 1;
create sequence faculty_sequence start 1 increment 1;
create sequence group_sequence start 1 increment 1;
create sequence lecture_sequence start 1 increment 1;
create sequence lesson_sequence start 1 increment 1;
create sequence student_sequence start 1 increment 1;
create sequence subject_sequence start 1 increment 1;
create sequence teacher_sequence start 1 increment 1;

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
    start_time timestamptz,
    subject_id bigint not null,
    primary key (id)
);

create table students
(
    id            bigint not null,
    first_name    varchar(128),
    last_name     varchar(128),
    middle_name   varchar(128),
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
    id          bigint not null,
    first_name  varchar(128),
    last_name   varchar(128),
    middle_name varchar(128),
    faculty_id  bigint not null,
    primary key (id)
);

alter table if exists groups
    add constraint group_faculty_fk
    foreign key (faculty_id) references faculties;

alter table if exists lectures
    add constraint lecture_audience_fk
    foreign key (audience_id) references audiences;

alter table if exists lectures
    add constraint lecture_group_fk
    foreign key (group_id) references groups;

alter table if exists lectures
    add constraint lecture_lesson_fk
    foreign key (lesson_id) references lessons;

alter table if exists lectures
    add constraint lecture_teacher_fk
    foreign key (teacher_id) references teachers;

alter table if exists lessons
    add constraint lesson_subject_fk
    foreign key (subject_id) references subjects;

alter table if exists students
    add constraint student_faculty_fk
    foreign key (faculty_id) references faculties;

alter table if exists students
    add constraint student_group_fk
    foreign key (group_id) references groups;

alter table if exists teachers
    add constraint teacher_faculty_fk
    foreign key (faculty_id) references faculties;