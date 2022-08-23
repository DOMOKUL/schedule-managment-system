package com.company.schedule.management.system.model.formatter;

import com.company.schedule.management.system.model.Lesson;
import com.company.schedule.management.system.service.LessonService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class LessonFormatter implements Formatter<Lesson> {

    private final LessonService lessonService;

    public LessonFormatter(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Override
    public Lesson parse(String text, Locale locale) throws ParseException {
        return lessonService.getLessonById(Long.valueOf(text));
    }

    @Override
    public String print(Lesson lesson, Locale locale) {
        return lesson.toString();
    }
}
