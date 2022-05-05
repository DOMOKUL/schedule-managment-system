package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.dao.LectureDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Lecture;
import com.company.schedule.management.system.service.LectureService;
import com.company.schedule.management.system.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LectureServiceImpl.class);
    private final LectureDao lectureDao;

    @Override
    public Lecture saveLecture(Lecture lecture) {
        try {
            return lectureDao.create(lecture);
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public Lecture getLectureById(Long id) {
        LOGGER.debug("Lecture at id = {} found: {}", id, lectureDao.findById(id).get());
        return lectureDao.findById(id).orElseThrow(() -> new ServiceException("Lecture with id: " + id + " doesn't exist"));
    }

    @Override
    public List<Lecture> getAllLectures() {
        LOGGER.debug("Lectures found:{}", lectureDao.findAll());
        try {
            return lectureDao.findAll();
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public Lecture updateLecture(Lecture lecture) {
        LOGGER.debug("Lecture has been updated: {}", lecture);
        try {
            return lectureDao.update(lecture);
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public void deleteLectureById(Long id) {
        if (lectureDao.findById(id).isPresent()) {
            LOGGER.debug("Lecture with id: {} has been deleted", id);
        }
        lectureDao.findById(id).orElseThrow(() -> new ServiceException("Lecture with id: " + id + " doesn't exist"));
        lectureDao.deleteById(id);
    }
}
