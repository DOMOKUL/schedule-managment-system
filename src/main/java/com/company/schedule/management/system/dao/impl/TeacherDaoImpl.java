package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.TeacherDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Teacher;
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
public class TeacherDaoImpl implements TeacherDao {

    private final EntityManager entityManager;

    @Override
    public Teacher create(Teacher teacher) {
        try {
            entityManager.persist(teacher);
            return teacher;
        } catch (EntityExistsException cause) {
            throw new DaoException("Teacher with id: " + teacher.getId() + " already exist", cause);
        }
    }

    @Override
    public Optional<Teacher> findById(Long id) {
        try {
            return Optional.ofNullable((Teacher) entityManager.createQuery("select t from Teacher t" +
                    " left join fetch t.faculty" +
                    " left join fetch t.lectures l" +
                    " left join fetch l.group g" +
                    " left join fetch g.faculty" +
                    " left join fetch l.audience" +
                    " left join fetch l.lesson le" +
                    " left join fetch le.subject where t.id =:id").setParameter("id", id).getSingleResult());
        } catch (NoResultException cause) {
            throw new DaoException("Teacher with id: " + id + " doesn't exist", cause);
        }
    }

    @Override
    public List<Teacher> findAll() {
        try {
            return entityManager.createQuery("select t from Teacher t").getResultList();
        } catch (IllegalArgumentException cause) {
            throw new DaoException(cause);
        }
    }

    @Override
    public Teacher update(Teacher teacher) {
        try {
            entityManager.merge(teacher);
        } catch (PersistenceException cause) {
            throw new DaoException("Update Error: " + cause.getMessage());
        }
        return teacher;
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            Teacher teacher = findById(id).get();
            entityManager.remove(teacher);
            return true;
        } catch (IllegalArgumentException cause) {
            throw new DaoException("Teacher with id: " + id + " doesn't exist", cause);
        }
    }
}
