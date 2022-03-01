package com.company.schedule.managment.system.models;

import java.util.*;
import java.sql.Time;
import java.time.Duration;

public class Lesson {

    private Long id;
    private Integer number;
    private Time startTime;
    private Duration duration;
    private Subject subject;
    private List<Lecture> lectures;

    public Lesson(Integer number, Time startTime, Duration duration, Subject subject, List<Lecture> lectures) {
        this.number = number;
        this.startTime = startTime;
        this.duration = duration;
        this.subject = subject;
        this.lectures = lectures;
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

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(id, lesson.id) && Objects.equals(number, lesson.number) && Objects.equals(startTime, lesson.startTime) && Objects.equals(duration, lesson.duration) && Objects.equals(subject, lesson.subject) && Objects.equals(lectures, lesson.lectures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, startTime, duration, subject, lectures);
    }
}
