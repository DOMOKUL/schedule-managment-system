package models;

import java.util.Objects;

public class Audience {

    private Long id;
    private Integer number;
    private Integer capacity;

    public Audience(Integer number, Integer capacity) {
        this.number = number;
        this.capacity = capacity;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return Objects.hash(id, number, capacity);
    }
}
