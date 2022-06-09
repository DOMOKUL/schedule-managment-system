package com.company.schedule.management.system.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
    @NotNull
    @Min(value = 1, message = "Number must be more than 1")
    @Max(value = 1000, message = "Number must be less than 1000")
    private Integer number;
    @NotNull
    @Min(value = 15, message = "Capacity must be more than 15")
    @Max(value = 100, message = "Capacity must be less than 100")
    private Integer capacity;
    @OneToMany(mappedBy = "audience", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
