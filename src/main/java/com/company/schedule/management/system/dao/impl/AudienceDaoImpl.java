package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.AudienceDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Audience;
import lombok.RequiredArgsConstructor;
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

    private final EntityManager entityManager;

    @Override
    public Audience create(Audience audience) {
        try {
            entityManager.persist(audience);
        } catch (DataIntegrityViolationException cause) {
            throw new DaoException("Audience with id: " + audience.getId() + " already exist", cause);
        }
        return audience;
    }

    @Override
    public Optional<Audience> findById(Long id) {
        try {
            return Optional.ofNullable((Audience) entityManager.createQuery("select a from Audience a " +
                    "left join fetch a.lectures l " +
                    "left join fetch l.group g " +
                    "left join fetch g.faculty " +
                    "left join fetch l.teacher t " +
                    "left join fetch t.faculty " +
                    "left join fetch l.lesson le " +
                    " left join fetch le.subject where a.id =:id").setParameter("id", id).getSingleResult());
        } catch (NoResultException cause) {
            throw new DaoException("Audience with id: " + id + " doesn't exist", cause);
        }
    }

    @Override
    public List<Audience> findAll() {
        try {
            return entityManager.createQuery("select a from Audience a").getResultList();
        } catch (IllegalArgumentException cause) {
            throw new DaoException(cause);
        }
    }

    @Override
    public Audience update(Audience audience) {
        try {
            entityManager.merge(audience);
        } catch (PersistenceException cause) {
            throw new DaoException("Update Error: " + cause.getMessage());
        }
        return audience;
    }

    @Override
    public boolean deleteById(Long id) {
        Audience audience = findById(id)
                .orElseThrow(() -> new DaoException("Audience with id: " + id + " doesn't exist"));
        entityManager.remove(audience);
        return true;
    }
}