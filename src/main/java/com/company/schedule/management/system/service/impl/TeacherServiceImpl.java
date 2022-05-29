package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.model.Teacher;
import com.company.schedule.management.system.repository.TeacherRepository;
import com.company.schedule.management.system.service.TeacherService;
import com.company.schedule.management.system.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherServiceImpl.class);
    private final TeacherRepository teacherRepository;

    @Override
    public Teacher saveTeacher(Teacher teacher) {
        try {
            return teacherRepository.saveAndFlush(teacher);
        } catch (DataIntegrityViolationException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public Teacher getTeacherById(Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        LOGGER.debug("Teacher at id = {} found: {}", id, teacher.get());
        return teacher.orElseThrow(() -> new ServiceException("Teacher with id: " + id + " doesn't exist"));
    }

    @Override
    public List<Teacher> getAllTeachers() {
        LOGGER.debug("Teachers found:{}", teacherRepository.findAll());
        return teacherRepository.findAll();
    }

    @Override
    public Teacher updateTeacher(Teacher teacher) {
        LOGGER.debug("Teacher has been updated: {}", teacher);
        try {
            return teacherRepository.saveAndFlush(teacher);
        } catch (DataIntegrityViolationException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public void deleteTeacherById(Long id) {
        if (teacherRepository.findById(id).isPresent()) {
            teacherRepository.deleteById(id);
            LOGGER.debug("Teacher with id: {} has been deleted", id);
        } else {
            throw new ServiceException("Audience with id: " + id + " doesn't exist");
        }

    }
}
