package com.company.schedule.managment.system.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "audiences")
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
