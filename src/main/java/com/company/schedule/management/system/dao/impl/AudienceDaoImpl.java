package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.AudienceDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Audience;
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
public class AudienceDaoImpl implements AudienceDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(AudienceDaoImpl.class);
    private final EntityManager entityManager;

    @Override
    public Audience create(Audience audience) {
        try {
            entityManager.persist(audience);
            entityManager.flush();
        } catch (DataIntegrityViolationException cause) {
            LOGGER.warn("Audience with id: " + audience.getId() + " already exist", cause);
            throw new DaoException("Audience with id: " + audience.getId() + " already exist", cause);
        }
        LOGGER.debug("Audience is created: {}", audience);
        return audience;
    }

    @Override
    public Optional<Audience> findById(Long id) {
        Optional<Audience> result = Optional.ofNullable((Audience) entityManager.createQuery("select a from Audience a " +
                "left join fetch a.lectures l " +
                "left join fetch l.group g " +
                "left join fetch g.faculty " +
                "left join fetch l.teacher t " +
                "left join fetch t.faculty " +
                "left join fetch l.lesson le " +
                " left join fetch le.subject where a.id =:id").setParameter("id", id).getSingleResult());
        LOGGER.debug("Audience at id = {} found: {}", id, result.get());
        try {
            return result;
        } catch (NoResultException cause) {
            LOGGER.warn("Audience with id: " + id + " doesn't exist", cause);
            return Optional.empty();
        }
    }

    @Override
    public List<Audience> findAll() {
        List<Audience> resultList = entityManager.createQuery("select a from Audience a").getResultList();
        LOGGER.debug("Audiences found:{}", resultList);
        try {
            return resultList;
        } catch (IllegalArgumentException cause) {
            LOGGER.warn("Audiences not found", cause);
            throw new DaoException(cause);
        }
    }

    @Override
    public Audience update(Audience audience) {
        try {
            entityManager.merge(audience);
        } catch (PersistenceException cause) {
            LOGGER.warn("Audience update error: " + cause);
            throw new DaoException("Audience update error: " + cause);
        }
        LOGGER.debug("Audience has been updated: {}", audience);
        return audience;
    }

    @Override
    public boolean deleteById(Long id) {
        if (findById(id).isEmpty()) {
            LOGGER.warn("Lecture with id: {} hasn't been deleted", id);
        }
        Audience audience = findById(id)
                .orElseThrow(() -> new DaoException("Audience with id: " + id + " doesn't exist"));
        entityManager.remove(audience);
        LOGGER.debug("Audience with id: {} has been deleted: {}", id, audience);
        return true;
    }
}