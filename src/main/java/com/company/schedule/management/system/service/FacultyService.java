package com.company.schedule.management.system.service;

import com.company.schedule.management.system.model.Faculty;

import java.util.List;

public interface FacultyService {

    Faculty saveFaculty(Faculty faculty);

    Faculty getFacultyById(Long id);

    List<Faculty> getAllFaculties();

    Faculty updateFaculty(Faculty faculty);

    boolean deleteFacultyById(Long id);

    List<Faculty> saveAllFaculties(List<Faculty> faculties);
}
