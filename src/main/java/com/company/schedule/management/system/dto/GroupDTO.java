package com.company.schedule.management.system.dto;

import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.model.Lecture;
import com.company.schedule.management.system.model.Student;
import lombok.*;

import java.util.List;

@Data
public class GroupDTO {

    private Long id;
    private String name;
    private Faculty faculty;
    private List<Student> students;
    private List<Lecture> lectures;
}
