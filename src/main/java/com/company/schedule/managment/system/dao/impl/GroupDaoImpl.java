package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.dao.GroupDao;
import com.company.schedule.managment.system.models.Group;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupDaoImpl implements GroupDao {

    private final JdbcTemplate jdbcTemplate;

    public GroupDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Group create(Group group) {
        jdbcTemplate.update("INSERT INTO groups VALUES (?,?,?)",
                group.getId(), group.getName(), group.getFaculty());
        return group;
    }

    @Override
    public Group findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM groups WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Group.class)).stream().findAny().orElse(null);
    }

    @Override
    public List<Group> findAll() {
        return jdbcTemplate.query("SELECT * From groups", new BeanPropertyRowMapper<>(Group.class));
    }

    @Override
    public boolean update(Group group) {
        var updateRowCount = jdbcTemplate.update("UPDATE groups SET name=?, faculty_id=? WHERE id=?",
                group.getName(), group.getFaculty(), group.getId());
        if (updateRowCount != 0) {
            return true;
        }
        throw new RuntimeException("Update isn't available");
    }

    @Override
    public boolean delete(Long id) {
        var updateRowCount = jdbcTemplate.update("DELETE FROM groups WHERE id=?", id);
        if (updateRowCount != 0) {
            return true;
        }
        throw new RuntimeException("Delete isn't available");
    }
}
