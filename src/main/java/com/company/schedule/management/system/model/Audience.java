package com.company.schedule.management.system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "audiences")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Audience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer number;
    private Integer capacity;
    @OneToMany(mappedBy = "audience", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Lecture> lectures;

    public Audience(Integer number, Integer capacity, List<Lecture> lectures) {
        this.number = number;
        this.capacity = capacity;
        this.lectures = lectures;
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

    @Override
    public int hashCode() {
        return Objects.hash(id, number, capacity, lectures);
    }
}
