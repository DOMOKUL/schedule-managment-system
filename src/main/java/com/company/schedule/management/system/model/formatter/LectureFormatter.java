package com.company.schedule.management.system.model.formatter;

import com.company.schedule.management.system.model.Lecture;
import com.company.schedule.management.system.service.LectureService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class LectureFormatter implements Formatter<Lecture> {

    private final LectureService lectureService;

    public LectureFormatter(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @Override
    public Lecture parse(String text, Locale locale) throws ParseException {
        return lectureService.getLectureById(Long.valueOf(text));
    }

    @Override
    public String print(Lecture lecture, Locale locale) {
        return lecture.toString();
    }
}
