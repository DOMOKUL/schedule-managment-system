package com.company.schedule.management.system.service;

import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.model.Group;

import java.util.List;

public interface FacultyService {

    Faculty saveFaculty(Faculty faculty);

    Faculty getFacultyById(Long id);

    List<Faculty> getAllFaculties();

    Faculty updateFaculty(Faculty faculty);

    boolean deleteFacultyById(Long id);

    List<Faculty> getFacultiesForGroups(List<Group> allGroups);
}
