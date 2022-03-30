package com.company.schedule.management.system.service;

import com.company.schedule.management.system.model.Subject;

import java.util.List;

public interface SubjectService {

    Subject saveSubject(Subject subject);

    Subject getSubjectById(Long id);

    List<Subject> getAllSubjects();

    Subject updateSubject(Subject subject);

    boolean deleteSubjectById(Long id);
}
