package com.company.schedule.managment.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "faculties")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Faculty {

    @Id
    @SequenceGenerator(name = "faculty_sequence",
            sequenceName = "faculty_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "faculty_sequence")
    private Long id;
    private String name;
    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Group> groups;
    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Teacher> teachers;

    public Faculty(String name, List<Group> groups, List<Teacher> teachers) {
        this.name = name;
        this.groups = groups;
        this.teachers = teachers;
    }
}
