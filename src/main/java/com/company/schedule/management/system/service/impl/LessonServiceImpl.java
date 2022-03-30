package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.dao.LessonDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Group;
import com.company.schedule.management.system.model.Lecture;
import com.company.schedule.management.system.model.Lesson;
import com.company.schedule.management.system.model.Student;
import com.company.schedule.management.system.service.LessonService;
import com.company.schedule.management.system.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonDao lessonDao;

    @Autowired
    public LessonServiceImpl(LessonDao lessonDao) {
        this.lessonDao = lessonDao;
    }

    @Override
    public Lesson saveLesson(Lesson lesson) {
        try {
            return lessonDao.create(lesson);
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public Lesson getLessonById(Long id) {
        return lessonDao.findById(id).orElseThrow(() -> new ServiceException("Lesson with id: " + id + " doesn't exist"));
    }

    @Override
    public List<Lesson> getAllLessons() {
        return lessonDao.findAll();
    }

    @Override
    public Lesson updateLesson(Lesson lesson) {
        try {
            return lessonDao.update(lesson);
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public boolean deleteLessonById(Long id) {
        try {
            return lessonDao.deleteById(id);
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }
}
