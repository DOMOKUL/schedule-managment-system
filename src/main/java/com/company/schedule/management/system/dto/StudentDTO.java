package com.company.schedule.management.system.dto;

import com.company.schedule.management.system.model.Group;
import lombok.*;

@Data
public class StudentDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private Integer courseNumber;
    private Group group;
}
