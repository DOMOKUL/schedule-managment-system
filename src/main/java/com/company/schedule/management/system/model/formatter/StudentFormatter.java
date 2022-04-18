package com.company.schedule.management.system.model.formatter;

import com.company.schedule.management.system.model.Student;
import com.company.schedule.management.system.service.StudentService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class StudentFormatter implements Formatter<Student> {

    private final StudentService studentService;

    public StudentFormatter(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public Student parse(String text, Locale locale) throws ParseException {
        return studentService.getStudentById(Long.valueOf(text));
    }

    @Override
    public String print(Student student, Locale locale) {
        return student.toString();
    }
}
