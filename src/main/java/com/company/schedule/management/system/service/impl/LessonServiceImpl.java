package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.dao.LessonDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Lesson;
import com.company.schedule.management.system.service.LessonService;
import com.company.schedule.management.system.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    @Autowired
    private final LessonDao lessonDao;

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
            throw new ServiceException("Lesson doesn't delete ", cause);
        }
    }
}
