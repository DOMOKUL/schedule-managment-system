package com.company.schedule.management.system.dto;

import com.company.schedule.management.system.model.Group;
import com.company.schedule.management.system.model.Teacher;
import lombok.*;

import java.util.List;

@Data
public class FacultyDTO {

    private Long id;
    private String name;
    private List<Group> groups;
    private List<Teacher> teachers;
}
