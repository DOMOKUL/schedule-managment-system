package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.dao.LectureDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Lecture;
import com.company.schedule.management.system.service.LectureService;
import com.company.schedule.management.system.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

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
        return lectureDao.findById(id).orElseThrow(() -> new ServiceException("Lecture with id: " + id + " doesn't exist"));
    }

    @Override
    public List<Lecture> getAllLectures() {
        try {
            return lectureDao.findAll();
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public Lecture updateLecture(Lecture lecture) {
        try {
            return lectureDao.update(lecture);
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public void deleteLectureById(Long id) {
        lectureDao.findById(id).orElseThrow(() -> new ServiceException("Lecture with id: " + id + " doesn't exist"));
        lectureDao.deleteById(id);
    }
}
