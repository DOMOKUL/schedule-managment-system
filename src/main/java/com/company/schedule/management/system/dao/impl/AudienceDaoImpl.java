package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.AudienceDao;
import com.company.schedule.management.system.model.Audience;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AudienceDaoImpl implements AudienceDao {

    private final EntityManager entityManager;

    @Override
    public Audience create(Audience audience) {
        entityManager.merge(audience);
        return audience;
    }

    @Override
    public Audience findById(Long id) {
        Query findByIdAudienceQuery = entityManager.createQuery("select distinct a from Audience a " +
                "left join fetch a.lectures l " +
                "left join fetch l.group g " +
                "left join fetch g.faculty " +
                "left join fetch l.teacher t " +
                "left join fetch t.faculty " +
                "left join fetch l.lesson le " +
                " left join fetch le.subject where a.id =:id");
        findByIdAudienceQuery.setParameter("id", id);
        return (Audience) findByIdAudienceQuery.getSingleResult();
    }

    @Override
    public List<Audience> findAll() {
        return entityManager.createQuery("select a from Audience a").getResultList();
    }

    @Override
    public boolean update(Audience audience) {
        Query updateAudienceQuery = entityManager.createQuery("update Audience set capacity=:capacity, number =: number" +
                " where id =: id");
        updateAudienceQuery.setParameter("capacity", audience.getCapacity());
        updateAudienceQuery.setParameter("number", audience.getNumber());
        updateAudienceQuery.setParameter("id", audience.getId());
        return updateAudienceQuery.executeUpdate() != 0;
    }

    @Override
    public boolean deleteById(Long id) {
        Query deleteAudienceQuery = entityManager.createQuery("delete Audience where id =: id");
        deleteAudienceQuery.setParameter("id", id);
        return deleteAudienceQuery.executeUpdate() != 0;
    }

}