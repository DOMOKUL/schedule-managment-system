package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.GroupDao;
import com.company.schedule.management.system.model.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class GroupDaoImpl implements GroupDao {

    private final EntityManager entityManager;

    @Override
    public Group create(Group group) {
        entityManager.merge(group);
        return group;
    }

    @Override
    public Group findById(Long id) {
        Query findByIdGroupQuery = entityManager.createQuery("select g from Group g" +
                " left join fetch g.lectures l " +
                " left join fetch l.teacher t " +
                " left join fetch t.faculty " +
                " left join fetch l.lesson le " +
                " left join fetch le.subject " +
                " left join fetch l.audience  " +
                " left join fetch g.faculty where g.id =:id");
        findByIdGroupQuery.setParameter("id", id);
        return (Group) findByIdGroupQuery.getSingleResult();
    }

    @Override
    public List<Group> findAll() {
        return entityManager.createQuery("select g from Group g").getResultList();
    }

    @Override
    public boolean update(Group group) {
        Query updateGroupQuery = entityManager.createQuery("update Group set name=:name where id =: id");
        updateGroupQuery.setParameter("name", group.getName());
        updateGroupQuery.setParameter("id", group.getId());
        return updateGroupQuery.executeUpdate() != 0;
    }

    @Override
    public boolean deleteById(Long id) {
        Query deleteGroupQuery = entityManager.createQuery("delete Group where id =: id");
        deleteGroupQuery.setParameter("id", id);
        return deleteGroupQuery.executeUpdate() != 0;
    }
}
