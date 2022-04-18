package com.company.schedule.management.system.model.formatter;

import com.company.schedule.management.system.model.Teacher;
import com.company.schedule.management.system.service.TeacherService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class TeacherFormatter implements Formatter<Teacher> {

    private final TeacherService teacherService;

    public TeacherFormatter(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Override
    public Teacher parse(String text, Locale locale) throws ParseException {
        return teacherService.getTeacherById(Long.valueOf(text));
    }

    @Override
    public String print(Teacher teacher, Locale locale) {
        return teacher.toString();
    }
}
