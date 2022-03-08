package com.company.schedule.managment.system.dao.impl;

import com.company.schedule.managment.system.dao.FacultyDao;
import com.company.schedule.managment.system.dao.exception.DaoException;
import com.company.schedule.managment.system.models.Faculty;
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
public class FacultyDaoImpl implements FacultyDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FacultyDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Faculty create(Faculty faculty) {
        SimpleJdbcInsert insertFaculty = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("faculties")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", faculty.getName());
        Number newId = insertFaculty.executeAndReturnKey(parameters);
        faculty.setId(newId.longValue());
        return new Faculty(newId.longValue(), faculty.getName(), null, null);
    }

    @Override
    public Faculty findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM faculties WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Faculty.class));
    }

    @Override
    public List<Faculty> findAll() {
        return jdbcTemplate.query("SELECT * From faculties", new BeanPropertyRowMapper<>(Faculty.class));
    }

    @Override
    public boolean update(Faculty faculty) {
        var updateRowCount = jdbcTemplate.update("UPDATE faculties SET name=? WHERE id=?",
                faculty.getName(), faculty.getId());
        if (updateRowCount != 0) {
            return true;
        }
        throw new DaoException("Update isn't available");
    }

    @Override
    public boolean delete(Long id) {
        var updateRowCount = jdbcTemplate.update("DELETE FROM faculties WHERE id=?", id);
        if (updateRowCount != 0) {
            return true;
        }
        throw new DaoException("Delete isn't available");
    }
}
