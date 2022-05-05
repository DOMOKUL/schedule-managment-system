package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.dao.StudentDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Student;
import com.company.schedule.management.system.service.StudentService;
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
public class StudentServiceImpl implements StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentDao studentDao;

    @Override
    public Student saveStudent(Student student) {
        try {
            return studentDao.create(student);
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public Student getStudentById(Long id) {
        LOGGER.debug("Student at id = {} found: {}", id, studentDao.findById(id).get());
        return studentDao.findById(id).orElseThrow(() -> new ServiceException("Student with id: " + id + " doesn't exist"));
    }

    @Override
    public List<Student> getAllStudents() {
        LOGGER.debug("Students found:{}", studentDao.findAll());
        try {
            return studentDao.findAll();
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public Student updateStudent(Student student) {
        LOGGER.debug("Student has been updated: {}", student);
        try {
            return studentDao.update(student);
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public void deleteStudentById(Long id) {
        if (studentDao.findById(id).isPresent()) {
            LOGGER.debug("Student with id: {} has been deleted", id);
        }
        studentDao.findById(id).orElseThrow(() -> new ServiceException("Student with id: " + id + " doesn't exist"));
        studentDao.deleteById(id);
    }
}
