package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.model.Lesson;
import com.company.schedule.management.system.repository.LessonRepository;
import com.company.schedule.management.system.service.LessonService;
import com.company.schedule.management.system.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LessonServiceImpl.class);
    private final LessonRepository lessonRepository;

    @Override
    public Lesson saveLesson(Lesson lesson) {
        try {
            return lessonRepository.saveAndFlush(lesson);
        } catch (DataIntegrityViolationException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public Lesson getLessonById(Long id) {
        Optional<Lesson> lesson = lessonRepository.findById(id);
        LOGGER.debug("Lesson at id = {} found: {}", id, lesson.get());
        return lessonRepository.findById(id).orElseThrow(() -> new ServiceException("Lesson with id: " + id + " doesn't exist"));
    }

    @Override
    public List<Lesson> getAllLessons() {
        LOGGER.debug("Lessons found:{}", lessonRepository.findAll());
        return lessonRepository.findAll();
    }

    @Override
    public Lesson updateLesson(Lesson lesson) {
        LOGGER.debug("Lesson has been updated: {}", lesson);
        try {
            return lessonRepository.saveAndFlush(lesson);
        } catch (DataIntegrityViolationException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public void deleteLessonById(Long id) {
        if (lessonRepository.findById(id).isPresent()) {
            lessonRepository.deleteById(id);
            LOGGER.debug("Lesson with id: {} has been deleted", id);
        } else {
            throw new ServiceException("Audience with id: " + id + " doesn't exist");
        }

    }

    @Override
    public List<Duration> getDurationsForLesson(List<Lesson> lessons) {
        LOGGER.debug("Getting durations for lessons {}", lessons);
        List<Duration> result = new ArrayList<>();
        for (Lesson lesson : lessons) {
            if (lesson == null) {
                result.add(null);
            } else {
                result.add(lesson.getDuration());
            }
        }
        LOGGER.info("Durations for lessons {} received successful", lessons);
        return result;
    }
}
