package com.company.schedule.managment.system.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "lessons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {

    @Id
    @SequenceGenerator(name = "lesson_sequence",
            sequenceName = "lesson_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "lesson_sequence")
    private Long id;
    private Integer number;
    private LocalTime startTime;
    private Duration duration;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Lecture> lectures;

    public Lesson(Duration duration, Integer number, LocalTime startTime, Subject subject, List<Lecture> lectures) {
        this.number = number;
        this.startTime = startTime;
        this.duration = duration;
        this.subject = subject;
        this.lectures = lectures;
    }
}
