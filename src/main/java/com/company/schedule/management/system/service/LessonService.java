package com.company.schedule.management.system.service;

import com.company.schedule.management.system.model.Lesson;

import java.util.List;

public interface LessonService {

    Lesson saveLesson(Lesson lesson);

    Lesson getLessonById(Long id);

    List<Lesson> getAllLessons();

    Lesson updateLesson(Lesson lesson);

    boolean deleteLessonById(Long id);
}
