package com.company.schedule.managment.system.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class Person {

    private String firstName;
    private String lastName;
    private String middleName;
}
