package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.StudentDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Student;
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
public class StudentDaoImpl implements StudentDao {

    private final EntityManager entityManager;

    @Override
    public Student create(Student student) {
        try {
            entityManager.persist(student);
            return student;
        } catch (EntityExistsException cause) {
            throw new DaoException("Student with id: " + student.getId() + " already exist", cause);
        }
    }

    @Override
    public Optional<Student> findById(Long id) {
        try {
            return Optional.ofNullable((Student) entityManager.createQuery("select s from Student s" +
                    " left join fetch s.group g" +
                    " left join fetch g.faculty where s.id = :id").setParameter("id", id).getSingleResult());
        } catch (NoResultException cause) {
            throw new DaoException("Student with id: " + id + " doesn't exist", cause);
        }
    }

    @Override
    public List<Student> findAll() {
        try {
            return entityManager.createQuery("select s from Student s").getResultList();
        } catch (IllegalArgumentException cause) {
            throw new DaoException(cause);
        }
    }

    @Override
    public Student update(Student student) {
        try {
            entityManager.merge(student);
        } catch (PersistenceException cause) {
            throw new DaoException("Update Error: " + cause.getMessage());
        }
        return student;
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            Student student = findById(id).get();
            entityManager.remove(student);
            return true;
        } catch (IllegalArgumentException cause) {
            throw new DaoException("Student with id: " + id + " doesn't exist", cause);
        }
    }
}
