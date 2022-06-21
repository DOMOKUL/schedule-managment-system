package com.company.schedule.management.system.dto;

import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.model.Lecture;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeacherDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private Faculty faculty;
    private List<Lecture> lectures;
}
