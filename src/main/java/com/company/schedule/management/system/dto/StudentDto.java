package com.company.schedule.management.system.dto;

import com.company.schedule.management.system.model.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private Integer courseNumber;
    private Group group;
}
