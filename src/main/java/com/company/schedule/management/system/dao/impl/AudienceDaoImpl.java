package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.AudienceDao;
import com.company.schedule.management.system.model.Audience;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AudienceDaoImpl implements AudienceDao {

    private final EntityManager entityManager;

    @Override
    public Audience create(Audience audience) {
        return null;
    }

    @Override
    public Audience findById(Long id) {
        return null;
    }

    @Override
    public List<Audience> findAll() {
        return null;
    }

    @Override
    public boolean update(Audience audience) {
        return true;
    }

    @Override
    public boolean delete(Long id) {
        return true;
    }

}