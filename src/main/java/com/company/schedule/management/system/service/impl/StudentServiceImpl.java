package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.model.Student;
import com.company.schedule.management.system.repository.StudentRepository;
import com.company.schedule.management.system.service.StudentService;
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
public class StudentServiceImpl implements StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;

    @Override
    public Student saveStudent(Student student) {
        try {
            return studentRepository.saveAndFlush(student);
        } catch (DataIntegrityViolationException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public Student getStudentById(Long id) {
        LOGGER.debug("Student at id = {} found: {}", id, studentRepository.findById(id).get());
        return studentRepository.findById(id).orElseThrow(() -> new ServiceException("Student with id: " + id + " doesn't exist"));
    }

    @Override
    public List<Student> getAllStudents() {
        LOGGER.debug("Students found:{}", studentRepository.findAll());
        return studentRepository.findAll();
    }

    @Override
    public Student updateStudent(Student student) {
        LOGGER.debug("Student has been updated: {}", student);
        try {
            return studentRepository.saveAndFlush(student);
        } catch (DataIntegrityViolationException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public void deleteStudentById(Long id) {
        if (studentRepository.findById(id).isPresent()) {
            studentRepository.deleteById(id);
            LOGGER.debug("Student with id: {} has been deleted", id);
        } else {
            throw new ServiceException("Audience with id: " + id + " doesn't exist");
        }

    }
}
