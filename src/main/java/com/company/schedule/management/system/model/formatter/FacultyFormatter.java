package com.company.schedule.management.system.model.formatter;

import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.service.FacultyService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class FacultyFormatter implements Formatter<Faculty> {

    private final FacultyService facultyService;

    public FacultyFormatter(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @Override
    public Faculty parse(String text, Locale locale) throws ParseException {
        return facultyService.getFacultyById(Long.valueOf(text));
    }

    @Override
    public String print(Faculty faculty, Locale locale) {
        return faculty.toString();
    }
}
