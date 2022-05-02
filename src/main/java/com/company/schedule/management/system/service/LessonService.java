package com.company.schedule.management.system.service;

import com.company.schedule.management.system.model.Lesson;

import java.time.Duration;
import java.util.List;

public interface LessonService {

    Lesson saveLesson(Lesson lesson);

    Lesson getLessonById(Long id);

    List<Lesson> getAllLessons();

    Lesson updateLesson(Lesson lesson);

    void deleteLessonById(Long id);

    List<Duration> getDurationsForLesson(List<Lesson> lessons);
}
