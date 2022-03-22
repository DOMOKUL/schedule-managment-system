package com.company.schedule.management.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    @SequenceGenerator(name = "group_sequence",
            sequenceName = "group_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "group_sequence")
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<Student> students;
    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<Lecture> lectures;

    public Group(String name, Faculty faculty, List<Student> students, List<Lecture> lectures) {
        this.name = name;
        this.faculty = faculty;
        this.students = students;
        this.lectures = lectures;
    }
}
