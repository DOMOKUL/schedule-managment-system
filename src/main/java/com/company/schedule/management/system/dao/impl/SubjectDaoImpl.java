package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.SubjectDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Subject;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SubjectDaoImpl implements SubjectDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectDaoImpl.class);
    private final EntityManager entityManager;

    @Override
    public Subject create(Subject subject) {
        try {
            entityManager.persist(subject);
            entityManager.flush();
        } catch (DataIntegrityViolationException cause) {
            LOGGER.warn("Subject with id: " + subject.getId() + " already exist", cause);
            throw new DaoException("Subject with id: " + subject.getId() + " already exist", cause);
        }
        LOGGER.debug("Subject is created: {}", subject);
        return subject;
    }

    @Override
    public Optional<Subject> findById(Long id) {
        Optional<Subject> result = Optional.ofNullable((Subject) entityManager.createQuery("select s from Subject s" +
                " left join fetch s.lessons  where s.id = :id").setParameter("id", id).getSingleResult());
        LOGGER.debug("Subject at id = {} found: {}", id, result.get());
        try {
            return result;
        } catch (NoResultException cause) {
            LOGGER.warn("Subject with id: " + id + " doesn't exist", cause);
            return Optional.empty();
        }
    }

    @Override
    public List<Subject> findAll() {
        List<Subject> resultList = entityManager.createQuery("select s from Subject s").getResultList();
        LOGGER.debug("Subjects found: {}", resultList);
        try {
            return resultList;
        } catch (IllegalArgumentException cause) {
            LOGGER.warn("Subjects not found", cause);
            throw new DaoException(cause);
        }
    }

    @Override
    public Subject update(Subject subject) {
        try {
            entityManager.merge(subject);
        } catch (PersistenceException cause) {
            LOGGER.warn("Subject update error: " + cause);
            throw new DaoException("Subject update error: " + cause.getMessage());
        }
        LOGGER.debug("Subject has been updated: {}", subject);
        return subject;
    }

    @Override
    public boolean deleteById(Long id) {
        if (findById(id).isEmpty()) {
            LOGGER.warn("Subject with id: {} hasn't been deleted", id);
        }
        Subject subject = findById(id)
                .orElseThrow(() -> new DaoException("Subject with id: " + id + " doesn't exist"));
        entityManager.remove(subject);
        LOGGER.debug("Subject with id: {} has been deleted: {}", id, subject);
        return true;
    }
}
