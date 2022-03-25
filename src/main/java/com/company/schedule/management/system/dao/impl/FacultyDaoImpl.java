package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.FacultyDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Faculty;
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
public class FacultyDaoImpl implements FacultyDao {

    private final EntityManager entityManager;

    @Override
    public Faculty create(Faculty faculty) {
        try {
            entityManager.persist(faculty);
            return faculty;
        } catch (EntityExistsException cause) {
            throw new DaoException("Faculty with id: " + faculty.getId() + " already exist", cause);
        }
    }

    @Override
    public Optional<Faculty> findById(Long id) {
        try {
            return Optional.ofNullable((Faculty) entityManager.createQuery("select f from Faculty f " +
                    "left join fetch f.groups where f.id = :id").setParameter("id", id).getSingleResult());
        } catch (NoResultException cause) {
            throw new DaoException("Faculty with id: " + id + " doesn't exist", cause);
        }
    }

    @Override
    public List<Faculty> findAll() {
        try {
            return entityManager.createQuery("select f from Faculty f").getResultList();
        } catch (IllegalArgumentException cause) {
            throw new DaoException(cause);
        }
    }

    @Override
    public Faculty update(Faculty faculty) {
        try {
            entityManager.merge(faculty);

        } catch (PersistenceException cause) {
            throw new DaoException("Update Error: " + cause.getMessage());
        }
        return faculty;
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            Faculty faculty = findById(id).get();
            entityManager.remove(faculty);
            return true;
        } catch (IllegalArgumentException cause) {
            throw new DaoException("Faculty with id: " + id + " doesn't exist", cause);
        }
    }
}
