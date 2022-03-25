package com.company.schedule.management.system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "teachers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Teacher extends Person {

    @Id
    @SequenceGenerator(name = "teacher_sequence",
            sequenceName = "teacher_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "teacher_sequence")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;
    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Lecture> lectures;

    public Teacher(Faculty faculty, List<Lecture> lectures) {
        this.faculty = faculty;
        this.lectures = lectures;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", faculty=" + faculty.getName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(id, teacher.id) && Objects.equals(faculty, teacher.faculty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, faculty);
    }
}
