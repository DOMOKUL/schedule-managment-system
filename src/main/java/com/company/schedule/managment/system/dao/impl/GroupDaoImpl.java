package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.dao.GroupDao;
import com.company.schedule.managment.system.dao.exception.DaoException;
import com.company.schedule.managment.system.model.Group;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class GroupDaoImpl implements GroupDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Group create(Group group) {
        SimpleJdbcInsert insertGroup = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("groups")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", group.getName());
        parameters.put("faculty_id", group.getFaculty().getId());
        Number newId = insertGroup.executeAndReturnKey(parameters);
        group.setId(newId.longValue());
        return new Group(newId.longValue(), group.getName(), group.getFaculty(), group.getStudents(), group.getLectures());
    }

    @Override
    public Group findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM groups WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Group.class));
    }

    @Override
    public List<Group> findAll() {
        return jdbcTemplate.query("SELECT * FROM groups", new BeanPropertyRowMapper<>(Group.class));
    }

    @Override
    public boolean update(Group group) {
        var updateRowCount = jdbcTemplate.update("UPDATE groups SET name=?, faculty_id=? WHERE id=?",
                group.getName(), group.getFaculty(), group.getId());
        if (updateRowCount != 0) {
            return true;
        }
        throw new DaoException("Update isn't available");
    }

    @Override
    public boolean delete(Long id) {
        var updateRowCount = jdbcTemplate.update("DELETE FROM groups WHERE id=?", id);
        if (updateRowCount != 0) {
            return true;
        }
        throw new DaoException("Delete isn't available");
    }
}
