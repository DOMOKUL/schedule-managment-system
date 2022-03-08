package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.dao.AudienceDao;
import com.company.schedule.managment.system.dao.exception.DaoException;
import com.company.schedule.managment.system.model.Audience;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class AudienceDaoImpl implements AudienceDao {

    private final JdbcTemplate jdbcTemplate;

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
        } catch (Exception cause) {
            throw new DaoException("Audience with id: " + audience.getId() + " already exist", cause);
        }
    }

    @Override
    public Audience findById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM audiences WHERE id=?", new Object[]{id},
                    new BeanPropertyRowMapper<>(Audience.class));
        } catch (InvalidResultSetAccessException cause) {
            throw new DaoException("Audience with id: " + id + " doesn't exist", cause);
        } catch (DataAccessException cause) {
            throw new DaoException("Trouble with access to database ", cause);
        }
    }

    @Override
    public List<Audience> findAll() {
        try {
            return jdbcTemplate.query("SELECT * FROM audiences", new BeanPropertyRowMapper<>(Audience.class));
        } catch (InvalidResultSetAccessException cause) {
            throw new DaoException("Audiences doesn't exist", cause);
        } catch (DataAccessException cause) {
            throw new DaoException("Trouble with access to database ", cause);
        }
    }

    @Override
    public boolean update(Audience audience) {
        var updateRowCount = jdbcTemplate.update("UPDATE audiences SET capacity=?,number=? WHERE id=?",
                audience.getCapacity(), audience.getNumber(), audience.getId());
        if (updateRowCount != 0) {
            return true;
        }
        throw new DaoException("Update isn't available");
    }

    @Override
    public boolean delete(Long id) {
        var updateRowCount = jdbcTemplate.update("DELETE FROM audiences WHERE id=? ", id);
        if (updateRowCount != 0) {
            return true;
        }
        throw new DaoException("Delete isn't available");
    }
}
