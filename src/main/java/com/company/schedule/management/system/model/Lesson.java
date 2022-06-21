package com.company.schedule.management.system.model;

import com.company.schedule.management.system.model.validator.DurationConstraint;
import com.company.schedule.management.system.model.validator.StartTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.sql.Time;
import java.time.Duration;
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
    @Min(value = 1, message = "Lesson number must be greater than 1")
    @Max(value = 6, message = "Lesson number must be less than 6")
    private Integer number;
    @StartTime
    private Time startTime;
    @DurationConstraint
    private Duration duration;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Subject subject;
    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Lecture> lectures;

    public Lesson(Integer number, Time startTime, Duration duration, Subject subject, List<Lecture> lectures) {
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
                ", subject=" + subject +
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
