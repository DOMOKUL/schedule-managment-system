package com.company.schedule.management.system.dto;

import com.company.schedule.management.system.model.Group;
import com.company.schedule.management.system.model.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FacultyDto {

    private Long id;
    private String name;
    private List<Group> groups;
    private List<Teacher> teachers;
}
