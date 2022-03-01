package com.company.schedule.managment.system.models;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Lecture {

    private Long id;
    private Integer number;
    private Date date;
    private Audience audience;
    private List<Group> groups;
    private Lesson lesson;
    private Teacher teacher;

    public Lecture(Integer number, Date date, Audience audience, List<Group> groups, Lesson lesson, Teacher teacher) {
        this.number = number;
        this.date = date;
        this.audience = audience;
        this.groups = groups;
        this.lesson = lesson;
        this.teacher = teacher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Audience getAudience() {
        return audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecture lecture = (Lecture) o;
        return Objects.equals(id, lecture.id) && Objects.equals(number, lecture.number) && Objects.equals(date, lecture.date) && Objects.equals(audience, lecture.audience) && Objects.equals(groups, lecture.groups) && Objects.equals(lesson, lecture.lesson) && Objects.equals(teacher, lecture.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, date, audience, groups, lesson, teacher);
    }
}
