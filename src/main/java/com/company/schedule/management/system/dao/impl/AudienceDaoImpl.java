package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.AudienceDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Audience;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AudienceDaoImpl implements AudienceDao {

    private final JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public AudienceDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Audience create(Audience audience) {
        try {
            SimpleJdbcInsert insertAudience = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("audiences")
                    .usingGeneratedKeyColumns("id");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("capacity", audience.getCapacity());
            parameters.put("number", audience.getNumber());
            Number newId = insertAudience.executeAndReturnKey(parameters);
            audience.setId(newId.longValue());
            return new Audience(newId.longValue(), audience.getNumber(), audience.getCapacity());
        } catch (DataIntegrityViolationException cause) {
            throw new DaoException("Audience with id: " + audience.getId() + " already exist", cause);
        }
    }

    @Override
    public Audience findById(Long id) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Audience> cq = criteriaBuilder.createQuery(Audience.class);

            Root<Audience> root = cq.from(Audience.class);
            Predicate audienceIdPredicate = criteriaBuilder.equal(root.get("id"), id);
            cq.where(audienceIdPredicate);

            TypedQuery<Audience> query = entityManager.createQuery(cq);
            return query.getSingleResult();
        } catch (InvalidResultSetAccessException cause) {
            throw new DaoException("Audience with id: " + id + " doesn't exist", cause);
        } catch (DataAccessException cause) {
            throw new DaoException("Trouble with access to database ", cause);
        }
    }

    @Override
    public List<Audience> findAll() {

        List<Audience> audiences = null;

        return audiences;
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