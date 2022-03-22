package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.FacultyDao;
import com.company.schedule.management.system.model.Faculty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FacultyDaoImpl implements FacultyDao {

    private final EntityManager entityManager;

    @Override
    public Faculty create(Faculty faculty) {
        entityManager.merge(faculty);
        return faculty;
    }

    @Override
    public Faculty findById(Long id) {
        Query findByIdFacultyQuery = entityManager.createQuery("select f from Faculty f " +
                "left join fetch f.groups where f.id = :id");
        findByIdFacultyQuery.setParameter("id", id);
        return (Faculty) findByIdFacultyQuery.getSingleResult();
    }

    @Override
    public List<Faculty> findAll() {
        return entityManager.createQuery("select f from Faculty f").getResultList();
    }

    @Override
    public boolean update(Faculty faculty) {
        Query updateFacultyQuery = entityManager.createQuery("update Faculty set name=:name where id =: id");
        updateFacultyQuery.setParameter("name", faculty.getName());
        updateFacultyQuery.setParameter("id", faculty.getId());
        return updateFacultyQuery.executeUpdate() != 0;
    }

    @Override
    public boolean deleteById(Long id) {
        Query deleteFacultyQuery = entityManager.createQuery("delete Faculty where id =: id");
        deleteFacultyQuery.setParameter("id", id);
        return deleteFacultyQuery.executeUpdate() != 0;
    }
}
