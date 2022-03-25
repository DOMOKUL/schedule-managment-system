package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.LessonDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LessonDaoImpl implements LessonDao {

    private final EntityManager entityManager;

    @Override
    public Lesson create(Lesson lesson) {
        try {
            entityManager.persist(lesson);
        } catch (EntityExistsException cause) {
            throw new DaoException("Lesson with id: " + lesson.getId() + " already exist", cause);
        }
        return lesson;
    }

    @Override
    public Optional<Lesson> findById(Long id) {
        try {
            return Optional.ofNullable((Lesson) entityManager.createQuery("select l from Lesson l" +
                    " left join fetch l.subject s" +
                    " left join fetch s.lessons where l.id = :id").setParameter("id", id).getSingleResult());
        } catch (NoResultException cause) {
            throw new DaoException("Lesson with id: " + id + " doesn't exist", cause);
        }
    }

    @Override
    public List<Lesson> findAll() {
        try {
            return entityManager.createQuery("select l from Lesson l").getResultList();
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
        return lesson;
    }

    @Override
    public boolean deleteById(Long id) {
        Lesson lesson = findById(id)
                .orElseThrow(() -> new DaoException("Lesson with id: " + id + " doesn't exist"));
        entityManager.remove(lesson);
        return true;
    }
}
