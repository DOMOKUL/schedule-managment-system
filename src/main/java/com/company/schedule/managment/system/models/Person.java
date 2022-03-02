package com.company.schedule.managment.system.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class Person {

    private String firstName;
    private String lastName;
    private String middleName;
}
