package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.StudentDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Student;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StudentDaoImpl implements StudentDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentDaoImpl.class);
    private final EntityManager entityManager;

    @Override
    public Student create(Student student) {
        try {
            entityManager.persist(student);
            entityManager.flush();
        } catch (InvalidDataAccessApiUsageException cause) {
            LOGGER.warn("Student with id: " + student.getId() + " already exist", cause);
            throw new DaoException("Student with id: " + student.getId() + " already exist", cause);
        }
        LOGGER.debug("Student is created: {}", student);
        return student;
    }

    @Override
    public Optional<Student> findById(Long id) {
        Optional<Student> result = Optional.ofNullable((Student) entityManager.createQuery("select s from Student s" +
                " left join fetch s.group g" +
                " left join fetch g.faculty where s.id = :id").setParameter("id", id).getSingleResult());
        LOGGER.debug("Student at id = {} found: {}", id, result.get());
        try {
            return result;
        } catch (NoResultException cause) {
            LOGGER.warn("Student with id: " + id + " doesn't exist", cause);
            return Optional.empty();
        }
    }

    @Override
    public List<Student> findAll() {
        List<Student> resultList = entityManager.createQuery("select s from Student s").getResultList();
        LOGGER.debug("Students found: {}", resultList);
        try {
            return resultList;
        } catch (IllegalArgumentException cause) {
            LOGGER.warn("Students not found", cause);
            throw new DaoException(cause);
        }
    }

    @Override
    public Student update(Student student) {
        try {
            entityManager.merge(student);
        } catch (PersistenceException cause) {
            LOGGER.warn("Student update error: " + cause);
            throw new DaoException("Update Error: " + cause.getMessage());
        }
        LOGGER.debug("Student has been updated: {}", student);
        return student;
    }

    @Override
    public boolean deleteById(Long id) {
        if (findById(id).isEmpty()) {
            LOGGER.warn("Student with id: {} hasn't been deleted", id);
        }
        Student student = findById(id)
                .orElseThrow(() -> new DaoException("Student with id: " + id + " doesn't exist"));
        entityManager.remove(student);
        LOGGER.debug("Student with id: {} has been deleted: {}", id, student);
        return true;
    }
}
