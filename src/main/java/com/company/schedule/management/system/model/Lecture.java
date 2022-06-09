package com.company.schedule.management.system.model;

import com.company.schedule.management.system.model.validator.CurrentDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "lectures")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(value = 1, message = "Lecture number must be greater than 1")
    @Max(value = 6, message = "Lecture number must be less than 6")
    private Integer number;
    @CurrentDate
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

    @Override
    public String toString() {
        return "Lecture{" +
                "id=" + id +
                ", number=" + number +
                ", date=" + date +
                ", audience=" + audience +
                ", group=" + group +
                ", lesson=" + lesson +
                ", teacher=" + teacher +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecture lecture = (Lecture) o;
        return Objects.equals(id, lecture.id) && Objects.equals(number, lecture.number) && Objects.equals(date, lecture.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, date, audience, group, lesson, teacher);
    }
}
