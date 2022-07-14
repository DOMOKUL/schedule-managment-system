package com.company.schedule.management.system.dto;

import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.model.Lecture;
import lombok.*;

import java.util.List;

@Data
public class TeacherDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private Faculty faculty;
    private List<Lecture> lectures;
}
