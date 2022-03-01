package com.company.schedule.managment.system.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.time.Duration;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @SequenceGenerator(name = "lesson_sequence",
            sequenceName = "lesson_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "lesson_sequence")
    private Long id;
    private Integer number;
    private Time startTime;
    private Duration duration;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Lecture> lectures;

    public Lesson(Integer number, Time startTime, Duration duration, Subject subject, List<Lecture> lectures) {
        this.number = number;
        this.startTime = startTime;
        this.duration = duration;
        this.subject = subject;
        this.lectures = lectures;
    }
}
