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
    id       int8 not null,
    capacity int4,
    number   int4,
    primary key (id)
);

create table faculties
(
    id   int8 not null,
    name varchar(255),
    primary key (id)
);

create table groups
(
    id         int8 not null,
    name       varchar(255),
    faculty_id int8 not null,
    primary key (id)
);

create table lectures
(
    id          int8 not null,
    date        timestamp,
    number      int4,
    audience_id int8 not null,
    group_id    int8 not null,
    lesson_id   int8 not null,
    teacher_id  int8 not null,
    primary key (id)
);

create table lessons
(
    id         int8 not null,
    duration   int8,
    number     int4,
    start_time time,
    subject_id int8 not null,
    primary key (id)
);

create table students
(
    id            int8 not null,
    course_number int4,
    faculty_id    int8 not null,
    group_id      int8 not null,
    primary key (id)
);

create table subjects
(
    id   int8 not null,
    name varchar(255),
    primary key (id)
);

create table teachers
(
    id         int8 not null,
    faculty_id int8 not null,
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