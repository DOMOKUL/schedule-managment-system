package com.company.schedule.managment.system.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "audiences")
@Data
@NoArgsConstructor
public class Audience {

    @Id
    @SequenceGenerator(name = "audience_sequence",
            sequenceName = "audience_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "audience_sequence")
    private Long id;
    private Integer number;
    private Integer capacity;

    public Audience(Integer number, Integer capacity) {
        this.number = number;
        this.capacity = capacity;
    }
}
