package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.LessonDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Lesson;
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
public class LessonDaoImpl implements LessonDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(LessonDaoImpl.class);
    private final EntityManager entityManager;

    @Override
    public Lesson create(Lesson lesson) {
        try {
            entityManager.persist(lesson);
            entityManager.flush();
        } catch (InvalidDataAccessApiUsageException cause) {
            throw new DaoException("Lesson with id: " + lesson.getId() + " already exist", cause);
        }
        LOGGER.debug("Lesson is created: {}", lesson);
        return lesson;
    }

    @Override
    public Optional<Lesson> findById(Long id) {
        Optional<Lesson> result = Optional.ofNullable((Lesson) entityManager.createQuery("select l from Lesson l" +
                " left join fetch l.subject s" +
                " left join fetch s.lessons where l.id = :id").setParameter("id", id).getSingleResult());
        LOGGER.debug("Lesson at id = {} found: {}", id, result.get());
        try {
            return result;
        } catch (NoResultException cause) {
            return Optional.empty();
        }
    }

    @Override
    public List<Lesson> findAll() {
        List<Lesson> resultList = entityManager.createQuery("select l from Lesson l").getResultList();
        LOGGER.debug("Lessons found: {}", resultList);
        try {
            return resultList;
        } catch (IllegalArgumentException cause) {
            throw new DaoException(cause);
        }
    }

    @Override
    public Lesson update(Lesson lesson) {
        try {
            entityManager.merge(lesson);
        } catch (PersistenceException cause) {
            throw new DaoException("Update Error: " + cause.getMessage());
        }
        LOGGER.debug("Lesson has been updated: {}", lesson);
        return lesson;
    }

    @Override
    public boolean deleteById(Long id) {
        Lesson lesson = findById(id)
                .orElseThrow(() -> new DaoException("Lesson with id: " + id + " doesn't exist"));
        entityManager.remove(lesson);
        LOGGER.debug("Lesson with id: {} has been deleted: {}",id,  lesson);
        return true;
    }
}
