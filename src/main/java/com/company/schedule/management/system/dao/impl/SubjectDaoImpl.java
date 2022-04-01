package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.SubjectDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Subject;
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
public class SubjectDaoImpl implements SubjectDao {

    private final EntityManager entityManager;

    @Override
    public Subject create(Subject subject) {
        try {
            entityManager.persist(subject);
        } catch (DataIntegrityViolationException cause) {
            throw new DaoException("Subject with id: " + subject.getId() + " already exist", cause);
        }
        return subject;
    }

    @Override
    public Optional<Subject> findById(Long id) {
        try {
            return Optional.ofNullable((Subject) entityManager.createQuery("select s from Subject s" +
                    " left join fetch s.lessons  where s.id = :id").setParameter("id", id).getSingleResult());
        } catch (NoResultException cause) {
            return Optional.empty();
        }
    }

    @Override
    public List<Subject> findAll() {
        try {
            return entityManager.createQuery("select s from Subject s").getResultList();
        } catch (IllegalArgumentException cause) {
            throw new DaoException(cause);
        }

    }

    @Override
    public Subject update(Subject subject) {
        try {
            entityManager.merge(subject);
        } catch (PersistenceException cause) {
            throw new DaoException("Update Error: " + cause.getMessage());
        }
        return subject;
    }

    @Override
    public boolean deleteById(Long id) {
        Subject subject = findById(id)
                .orElseThrow(() -> new DaoException("Subject with id: " + id + " doesn't exist"));
        entityManager.remove(subject);
        return true;
    }
}
