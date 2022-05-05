package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.GroupDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Group;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.awt.*;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GroupDaoImpl implements GroupDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupDaoImpl.class);
    private final EntityManager entityManager;

    @Override
    public Group create(Group group) {
        try {
            entityManager.persist(group);
            entityManager.flush();
        } catch (DataIntegrityViolationException cause) {
            throw new DaoException("Group with id: " + group.getId() + " already exist", cause);
        }
        LOGGER.debug("Group is created: {}", group);
        return group;
    }

    @Override
    public Optional<Group> findById(Long id) {
        Optional<Group> result = Optional.ofNullable((Group) entityManager.createQuery("select g from Group g" +
                " left join fetch g.lectures l " +
                " left join fetch l.teacher t " +
                " left join fetch t.faculty " +
                " left join fetch l.lesson le " +
                " left join fetch le.subject " +
                " left join fetch l.audience  " +
                " left join fetch g.faculty where g.id =:id").setParameter("id", id).getSingleResult());
        LOGGER.debug("Group at id = {} found: {}", id, result.get());
        try {
            return result;
        } catch (NoResultException cause) {
            return Optional.empty();
        }
    }

    @Override
    public List<Group> findAll() {
        List<Group> resultList = entityManager.createQuery("select g from Group g").getResultList();
        LOGGER.debug("Groups found: {}", resultList);
        try {
            return resultList;
        } catch (IllegalArgumentException cause) {
            throw new DaoException(cause);
        }
    }

    @Override
    public Group update(Group group) {
        try {
            entityManager.merge(group);
        } catch (ConstraintViolationException cause) {
            throw new DaoException("Update Error: " + cause.getMessage());
        }
        LOGGER.debug("Group has been updated: {}", group);
        return group;
    }

    @Override
    public boolean deleteById(Long id) {
        Group group = findById(id)
                .orElseThrow(() -> new DaoException("Group with id: " + id + " doesn't exist"));
        entityManager.remove(group);
        LOGGER.debug("Group with id: {} has been deleted: {}",id, group);
        return true;
    }
}
