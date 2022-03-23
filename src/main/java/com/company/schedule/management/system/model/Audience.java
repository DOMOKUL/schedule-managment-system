package com.company.schedule.management.system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "audiences")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @OneToMany(mappedBy = "audience", fetch = FetchType.LAZY)
    private List<Lecture> lectures;

    public Audience(Integer number, Integer capacity) {
        this.number = number;
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Audience{" +
                "id=" + id +
                ", number=" + number +
                ", capacity=" + capacity +
                ", lectures=" + lectures +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Audience audience = (Audience) o;
        return Objects.equals(id, audience.id) && Objects.equals(number, audience.number) && Objects.equals(capacity, audience.capacity);
    }
}
