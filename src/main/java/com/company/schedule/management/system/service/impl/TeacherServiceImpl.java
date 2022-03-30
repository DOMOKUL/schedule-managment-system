package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.dao.TeacherDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Teacher;
import com.company.schedule.management.system.service.TeacherService;
import com.company.schedule.management.system.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherDao teacherDao;

    @Autowired
    public TeacherServiceImpl(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

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
    public boolean deleteTeacherById(Long id) {
        try {
            return teacherDao.deleteById(id);
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }
}
