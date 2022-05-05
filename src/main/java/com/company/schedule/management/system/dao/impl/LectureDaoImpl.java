package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.LectureDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Lecture;
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
public class LectureDaoImpl implements LectureDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(LectureDaoImpl.class);
    private final EntityManager entityManager;

    @Override
    public Lecture create(Lecture lecture) {
        try {
            entityManager.persist(lecture);
            entityManager.flush();
        } catch (InvalidDataAccessApiUsageException cause) {
            LOGGER.warn("Lecture with id: " + lecture.getId() + " already exist", cause);
            throw new DaoException("Lecture with id: " + lecture.getId() + " already exist", cause);
        }
        LOGGER.debug("Lecture is created: {}", lecture);
        return lecture;
    }

    @Override
    public Optional<Lecture> findById(Long id) {
        Optional<Lecture> result = Optional.ofNullable((Lecture) entityManager.createQuery("select l from Lecture l" +
                " left join l.group g left join g.faculty left join l.audience" +
                " left join l.lesson le left join le.subject left join l.teacher" +
                " t left join t.faculty where l.id =:id").setParameter("id", id).getSingleResult());
        LOGGER.debug("Lecture at id = {} found: {}", id, result.get());
        try {
            return result;
        } catch (NoResultException cause) {
            LOGGER.warn("Lecture with id: " + id + " doesn't exist", cause);
            return Optional.empty();
        }
    }

    @Override
    public List<Lecture> findAll() {
        List<Lecture> resultList = entityManager.createQuery("select l from Lecture l" +
                " left join fetch l.group g" +
                " left join fetch g.faculty" +
                " left join fetch l.audience" +
                " left join fetch l.lesson le" +
                " left join fetch le.subject" +
                " left join fetch l.teacher" +
                " t left join fetch t.faculty").getResultList();
        LOGGER.debug("Lectures found: {}", resultList);
        try {
            return resultList;
        } catch (IllegalArgumentException cause) {
            LOGGER.warn("Lectures not found", cause);
            throw new DaoException(cause);
        }
    }

    @Override
    public Lecture update(Lecture lecture) {
        try {
            entityManager.merge(lecture);
        } catch (PersistenceException cause) {
            LOGGER.warn("Lecture update error: " + cause);
            throw new DaoException("Lecture update error: " + cause.getMessage());
        }
        LOGGER.debug("Lecture has been updated: {}", lecture);
        return lecture;
    }

    @Override
    public boolean deleteById(Long id) {
        if (findById(id).isEmpty()) {
            LOGGER.warn("Lecture with id: {} hasn't been deleted", id);
        }
        Lecture lecture = findById(id)
                .orElseThrow(() -> new DaoException("Lecture with id: " + id + " doesn't exist"));
        entityManager.remove(lecture);
        LOGGER.debug("Lecture with id: {} has been deleted: {}", id, lecture);
        return true;
    }
}
