package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.dao.TeacherDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Teacher;
import com.company.schedule.management.system.service.TeacherService;
import com.company.schedule.management.system.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherDao teacherDao;

    @Override
    public Teacher saveTeacher(Teacher teacher) {
        try {
            return teacherDao.create(teacher);
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherDao.findById(id).orElseThrow(() -> new ServiceException("Teacher with id: " + id + " doesn't exist"));
    }

    @Override
    public List<Teacher> getAllTeachers() {
        try {
            return teacherDao.findAll();
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public Teacher updateTeacher(Teacher teacher) {
        try {
            return teacherDao.update(teacher);
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public void deleteTeacherById(Long id) {
        teacherDao.findById(id).orElseThrow(() -> new ServiceException("Teacher with id: " + id + " doesn't exist"));
        teacherDao.deleteById(id);
    }
}
