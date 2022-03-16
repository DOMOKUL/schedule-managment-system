package com.company.schedule.management.system.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class Person {

    private String firstName;
    private String lastName;
    private String middleName;
}
