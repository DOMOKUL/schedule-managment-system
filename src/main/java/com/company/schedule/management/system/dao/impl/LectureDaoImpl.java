package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.LectureDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Lecture;
import lombok.RequiredArgsConstructor;
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

    private final EntityManager entityManager;

    @Override
    public Lecture create(Lecture lecture) {
        try {
            entityManager.persist(lecture);
        } catch (InvalidDataAccessApiUsageException cause) {
            throw new DaoException("Lecture with id: " + lecture.getId() + " already exist", cause);
        }
        return lecture;
    }

    @Override
    public Optional<Lecture> findById(Long id) {
        try {
            return Optional.ofNullable((Lecture) entityManager.createQuery("select l from Lecture l" +
                    " left join l.group g left join g.faculty left join l.audience" +
                    " left join l.lesson le left join le.subject left join l.teacher" +
                    " t left join t.faculty where l.id =:id").setParameter("id", id).getSingleResult());
        } catch (NoResultException cause) {
            throw new DaoException("Lecture with id: " + id + " doesn't exist", cause);
        }
    }

    @Override
    public List<Lecture> findAll() {
        try {
            return entityManager.createQuery("select l from Lecture l" +
                    " left join fetch l.group g" +
                    " left join fetch g.faculty" +
                    " left join fetch l.audience" +
                    " left join fetch l.lesson le" +
                    " left join fetch le.subject" +
                    " left join fetch l.teacher" +
                    " t left join fetch t.faculty").getResultList();
        } catch (IllegalArgumentException cause) {
            throw new DaoException(cause);
        }
    }

    @Override
    public Lecture update(Lecture lecture) {
        try {
            entityManager.merge(lecture);

        } catch (PersistenceException cause) {
            throw new DaoException("Update Error: " + cause.getMessage());
        }
        return lecture;
    }

    @Override
    public boolean deleteById(Long id) {
        Lecture lecture = findById(id)
                .orElseThrow(() -> new DaoException("Lecture with id: " + id + " doesn't exist"));
        entityManager.remove(lecture);
        return true;
    }
}
