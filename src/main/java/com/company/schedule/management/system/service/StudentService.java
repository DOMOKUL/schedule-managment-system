package com.company.schedule.management.system.service;

import com.company.schedule.management.system.model.Student;

import java.util.List;

public interface StudentService {

    Student saveStudent(Student student);

    Student getStudentById(Long id);

    List<Student> getAllStudents();

    Student updateStudent(Student student);

    boolean deleteStudentById(Long id);
}
