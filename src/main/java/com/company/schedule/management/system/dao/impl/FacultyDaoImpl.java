package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.FacultyDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Faculty;
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
public class FacultyDaoImpl implements FacultyDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FacultyDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Faculty create(Faculty faculty) {
        try {
            SimpleJdbcInsert insertFaculty = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("faculties")
                    .usingGeneratedKeyColumns("id");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("name", faculty.getName());
            Number newId = insertFaculty.executeAndReturnKey(parameters);
            faculty.setId(newId.longValue());
            return new Faculty(newId.longValue(), faculty.getName(), null, null);
        } catch (Exception cause) {
            throw new DaoException("Faculty with id: " + faculty.getId() + " already exist", cause);
        }
    }


    @Override
    public Faculty findById(Long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM faculties WHERE id=?", new Object[]{id},
                    new BeanPropertyRowMapper<>(Faculty.class));
        } catch (InvalidResultSetAccessException cause) {
            throw new DaoException("Faculty with id: " + id + " doesn't exist", cause);
        } catch (DataAccessException cause) {
            throw new DaoException("Trouble with access to database ", cause);
        }
    }

    @Override
    public List<Faculty> findAll() {
        try {
            return jdbcTemplate.query("SELECT * FROM faculties", new BeanPropertyRowMapper<>(Faculty.class));
        } catch (InvalidResultSetAccessException cause) {
            throw new DaoException("Faculties doesn't exist", cause);
        } catch (DataAccessException cause) {
            throw new DaoException("Trouble with access to database ", cause);
        }
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
