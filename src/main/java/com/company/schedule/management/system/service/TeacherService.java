package com.company.schedule.management.system.service;

import com.company.schedule.management.system.model.Teacher;

import java.util.List;

public interface TeacherService {

    Teacher saveTeacher(Teacher teacher);

    Teacher getTeacherById(Long id);

    List<Teacher> getAllTeachers();

    Teacher updateTeacher(Teacher teacher);

    void deleteTeacherById(Long id);
}
