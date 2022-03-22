package com.company.schedule.management.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "teachers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends Person {

    @Id
    @SequenceGenerator(name = "teacher_sequence",
            sequenceName = "teacher_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "teacher_sequence")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;
    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private List<Lecture> lectures;

    public Teacher(Faculty faculty, List<Lecture> lectures) {
        this.faculty = faculty;
        this.lectures = lectures;
    }
}
