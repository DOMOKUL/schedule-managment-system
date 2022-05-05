package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.TeacherDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Teacher;
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
public class TeacherDaoImpl implements TeacherDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherDaoImpl.class);
    private final EntityManager entityManager;

    @Override
    public Teacher create(Teacher teacher) {
        try {
            entityManager.persist(teacher);
            entityManager.flush();
        } catch (InvalidDataAccessApiUsageException cause) {
            LOGGER.warn("Teacher with id: " + teacher.getId() + " already exist", cause);
            throw new DaoException("Teacher with id: " + teacher.getId() + " already exist", cause);
        }
        LOGGER.debug("Teacher is created: {}", teacher);
        return teacher;
    }

    @Override
    public Optional<Teacher> findById(Long id) {
        Optional<Teacher> result = Optional.ofNullable((Teacher) entityManager.createQuery("select t from Teacher t" +
                " left join fetch t.faculty" +
                " left join fetch t.lectures l" +
                " left join fetch l.group g" +
                " left join fetch g.faculty" +
                " left join fetch l.audience" +
                " left join fetch l.lesson le" +
                " left join fetch le.subject where t.id =:id").setParameter("id", id).getSingleResult());
        LOGGER.debug("Teacher at id = {} found: {}", id, result.get());
        try {
            return result;
        } catch (NoResultException cause) {
            LOGGER.warn("Subject with id: " + id + " doesn't exist", cause);
            return Optional.empty();
        }
    }

    @Override
    public List<Teacher> findAll() {
        List<Teacher> resultList = entityManager.createQuery("select t from Teacher t").getResultList();
        LOGGER.debug("Teachers found: {}", resultList);
        try {
            return resultList;
        } catch (IllegalArgumentException cause) {
            LOGGER.warn("Teachers not found", cause);
            throw new DaoException(cause);
        }
    }

    @Override
    public Teacher update(Teacher teacher) {
        try {
            entityManager.merge(teacher);
        } catch (PersistenceException cause) {
            LOGGER.warn("Teacher update error: " + cause);
            throw new DaoException("Teacher update error: " + cause.getMessage());
        }
        LOGGER.debug("Teacher has been updated: {}", teacher);
        return teacher;
    }

    @Override
    public boolean deleteById(Long id) {
        if (findById(id).isEmpty()) {
            LOGGER.warn("Teacher with id: {} hasn't been deleted", id);
        }
        Teacher teacher = findById(id)
                .orElseThrow(() -> new DaoException("Teacher with id: " + id + " doesn't exist"));
        entityManager.remove(teacher);
        LOGGER.debug("Teacher with id: {} has been deleted: {}", id, teacher);
        return true;
    }
}
