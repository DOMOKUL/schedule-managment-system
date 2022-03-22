package com.company.schedule.management.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "lectures")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {

    @Id
    @SequenceGenerator(name = "lecture_sequence",
            sequenceName = "lecture_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "lecture_sequence")
    private Long id;
    private Integer number;
    private Date date;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audience_id")
    private Audience audience;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    public Lecture(Integer number, Date date, Audience audience, Group group, Lesson lesson, Teacher teacher) {
        this.number = number;
        this.date = date;
        this.audience = audience;
        this.group = group;
        this.lesson = lesson;
        this.teacher = teacher;
    }

}
