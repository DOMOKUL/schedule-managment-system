package com.company.schedule.management.system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "lessons")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer number;
    private LocalTime startTime;
    private Duration duration;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Lecture> lectures;

    public Lesson(Integer number, LocalTime startTime, Duration duration, Subject subject, List<Lecture> lectures) {
        this.number = number;
        this.startTime = startTime;
        this.duration = duration;
        this.subject = subject;
        this.lectures = lectures;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", number=" + number +
                ", startTime=" + startTime +
                ", duration=" + duration +
                ", subject=" + subject.getName() +
                ", lectures=" + lectures +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(id, lesson.id) && Objects.equals(number, lesson.number) && Objects.equals(startTime, lesson.startTime) && Objects.equals(duration, lesson.duration) && Objects.equals(subject, lesson.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, startTime, duration, subject);
    }
}
