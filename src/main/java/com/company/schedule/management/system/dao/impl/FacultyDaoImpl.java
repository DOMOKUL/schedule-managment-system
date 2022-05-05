package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.FacultyDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Faculty;
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
public class FacultyDaoImpl implements FacultyDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(FacultyDaoImpl.class);
    private final EntityManager entityManager;

    @Override
    public Faculty create(Faculty faculty) {
        try {
            entityManager.persist(faculty);
            entityManager.flush();
        } catch (DataIntegrityViolationException cause) {
            LOGGER.warn("Faculty with id: " + faculty.getId() + " already exist", cause);
            throw new DaoException("Faculty with id: " + faculty.getId() + " already exist", cause);
        }
        LOGGER.debug("Faculty is created: {}", faculty);
        return faculty;
    }

    @Override
    public Optional<Faculty> findById(Long id) {
        Optional<Faculty> result = Optional.ofNullable((Faculty) entityManager.createQuery("select f from Faculty f " +
                "left join fetch f.groups where f.id = :id").setParameter("id", id).getSingleResult());
        LOGGER.debug("Faculty at id = {} found: {}", id, result.get());
        try {
            return result;
        } catch (NoResultException cause) {
            LOGGER.warn("Faculty with id: " + id + " doesn't exist", cause);
            return Optional.empty();
        }
    }

    @Override
    public List<Faculty> findAll() {
        List<Faculty> resultList = entityManager.createQuery("select f from Faculty f").getResultList();
        LOGGER.debug("Faculties found: {}", resultList);
        try {
            return resultList;
        } catch (IllegalArgumentException cause) {
            LOGGER.warn("Faculties not found", cause);
            throw new DaoException(cause);
        }
    }

    @Override
    public Faculty update(Faculty faculty) {
        try {
            entityManager.merge(faculty);
        } catch (PersistenceException cause) {
            LOGGER.warn("Faculty update error: " + cause);
            throw new DaoException("Faculty update error: " + cause.getMessage());
        }
        LOGGER.debug("Faculty has been updated: {}", faculty);
        return faculty;
    }

    @Override
    public boolean deleteById(Long id) {
        if (findById(id).isEmpty()) {
            LOGGER.warn("Faculty with id: {} hasn't been deleted", id);
        }
        Faculty faculty = findById(id)
                .orElseThrow(() -> new DaoException("Faculty with id: " + id + " doesn't exist"));
        entityManager.remove(faculty);
        LOGGER.debug("Faculty with id: {} has been deleted: {}", id, faculty);
        return true;
    }
}
