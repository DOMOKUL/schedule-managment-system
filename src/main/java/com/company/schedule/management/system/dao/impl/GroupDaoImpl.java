package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.GroupDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Group;
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
public class GroupDaoImpl implements GroupDao {

    private final EntityManager entityManager;

    @Override
    public Group create(Group group) {
        try {
            entityManager.persist(group);
            return group;
        } catch (EntityExistsException cause) {
            throw new DaoException("Group with id: " + group.getId() + " already exist", cause);
        }
    }

    @Override
    public Optional<Group> findById(Long id) {
        try {
            return Optional.ofNullable((Group) entityManager.createQuery("select g from Group g" +
                    " left join fetch g.lectures l " +
                    " left join fetch l.teacher t " +
                    " left join fetch t.faculty " +
                    " left join fetch l.lesson le " +
                    " left join fetch le.subject " +
                    " left join fetch l.audience  " +
                    " left join fetch g.faculty where g.id =:id").setParameter("id", id).getSingleResult());
        } catch (NoResultException cause) {
            throw new DaoException("Group with id: " + id + " doesn't exist", cause);
        }
    }

    @Override
    public List<Group> findAll() {
        try {
            return entityManager.createQuery("select g from Group g").getResultList();
        } catch (IllegalArgumentException cause) {
            throw new DaoException(cause);
        }
    }

    @Override
    public Group update(Group group) {
        try {
            entityManager.merge(group);

        } catch (PersistenceException cause) {
            throw new DaoException("Update Error: " + cause.getMessage());
        }
        return group;
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            Group group = findById(id).get();
            entityManager.remove(group);
            return true;
        } catch (IllegalArgumentException cause) {
            throw new DaoException("Group with id: " + id + " doesn't exist", cause);
        }
    }
}
