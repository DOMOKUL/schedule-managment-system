package com.company.schedule.management.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    @Id
    @SequenceGenerator(name = "subject_sequence",
            sequenceName = "subject_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "subject_sequence")
    private Long id;
    private String name;
    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    private List<Lesson> lessons;

    public Subject(String name) {
        this.name = name;
    }
}
