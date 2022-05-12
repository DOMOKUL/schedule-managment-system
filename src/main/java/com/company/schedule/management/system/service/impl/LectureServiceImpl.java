package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.model.Lecture;
import com.company.schedule.management.system.repository.LectureRepository;
import com.company.schedule.management.system.service.LectureService;
import com.company.schedule.management.system.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LectureServiceImpl implements LectureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LectureServiceImpl.class);
    private final LectureRepository lectureRepository;

    @Override
    public Lecture saveLecture(Lecture lecture) {
        try {
            return lectureRepository.saveAndFlush(lecture);
        } catch (DataIntegrityViolationException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public Lecture getLectureById(Long id) {
        LOGGER.debug("Lecture at id = {} found: {}", id, lectureRepository.findById(id).get());
        return lectureRepository.findById(id).orElseThrow(() -> new ServiceException("Lecture with id: " + id + " doesn't exist"));
    }

    @Override
    public List<Lecture> getAllLectures() {
        LOGGER.debug("Lectures found:{}", lectureRepository.findAll());
        return lectureRepository.findAll();
    }

    @Override
    public Lecture updateLecture(Lecture lecture) {
        LOGGER.debug("Lecture has been updated: {}", lecture);
        try {
            return lectureRepository.saveAndFlush(lecture);
        } catch (DataIntegrityViolationException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public void deleteLectureById(Long id) {
        if (lectureRepository.findById(id).isPresent()) {
            lectureRepository.deleteById(id);
            LOGGER.debug("Lecture with id: {} has been deleted", id);
        } else {
            throw new ServiceException("Audience with id: " + id + " doesn't exist");
        }

    }
}
