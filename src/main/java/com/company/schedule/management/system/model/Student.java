package com.company.schedule.management.system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;

@Entity
@Table(name = "students")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(value = 1, message = "Student's course must be more than 1")
    @Max(value = 4, message = "Student's course must be less than 4")
    private Integer courseNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    public Student(String firstName, String lastName, String middleName, Integer courseNumber, Group group) {
        super(firstName, lastName, middleName);
        this.courseNumber = courseNumber;
        this.group = group;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", courseNumber=" + courseNumber +
                ", group=" + group +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(courseNumber, student.courseNumber) && Objects.equals(group, student.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseNumber, group);
    }
}
