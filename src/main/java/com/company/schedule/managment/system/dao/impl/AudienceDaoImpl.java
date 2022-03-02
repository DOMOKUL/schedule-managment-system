package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.dao.AudienceDao;
import com.company.schedule.managment.system.models.Audience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AudienceDaoImpl implements AudienceDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AudienceDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Audience create(Audience audience) {
        jdbcTemplate.update("INSERT INTO audiences VALUES (?,?,?)",
                audience.getId(), audience.getCapacity(), audience.getNumber());
        return audience;
    }

    @Override
    public Audience findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM audiences WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Audience.class)).stream().findAny().orElse(null);
    }

    @Override
    public List<Audience> findAll() {
        return jdbcTemplate.query("SELECT * From audiences", new BeanPropertyRowMapper<>(Audience.class));
    }

    @Override
    public boolean update(Audience audience) {
        var updateRowCount = jdbcTemplate.update("UPDATE audiences SET capacity=?,number=? WHERE id=?",
                audience.getCapacity(), audience.getNumber(), audience.getId());
        if (updateRowCount != 0) {
            return true;
        }
        throw new RuntimeException("Update isn't available");
    }

    @Override
    public boolean delete(Long id) {
        var updateRowCount = jdbcTemplate.update("DELETE FROM audiences WHERE id=?", id);
        if (updateRowCount != 0) {
            return true;
        }
        throw new RuntimeException("Delete isn't available");
    }
}
