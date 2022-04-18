package com.company.schedule.management.system.model.formatter;

import com.company.schedule.management.system.model.Subject;
import com.company.schedule.management.system.service.SubjectService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class SubjectFormatter implements Formatter<Subject> {

    private final SubjectService subjectService;

    public SubjectFormatter(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public Subject parse(String text, Locale locale) throws ParseException {
        return subjectService.getSubjectById(Long.valueOf(text));
    }

    @Override
    public String print(Subject subject, Locale locale) {
        return subject.toString();
    }
}
