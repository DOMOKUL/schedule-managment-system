package com.company.schedule.managment.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public Subject(String name) {
        this.name = name;
    }
}
