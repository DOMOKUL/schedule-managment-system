drop table if exists teachers cascade ;

drop table if exists students cascade ;

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

create table teachers
(
    id          bigint not null,
    first_name  varchar(128),
    last_name   varchar(128),
    middle_name varchar(128),
    faculty_id  bigint not null,
    primary key (id)
);
