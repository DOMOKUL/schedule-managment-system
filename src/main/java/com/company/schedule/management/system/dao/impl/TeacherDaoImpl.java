package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.TeacherDao;
import com.company.schedule.management.system.model.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TeacherDaoImpl implements TeacherDao {

    private final EntityManager entityManager;

    @Override
    public Teacher create(Teacher teacher) {
        entityManager.merge(teacher);
        return teacher;
    }

    @Override
    public Teacher findById(Long id) {
        Query findByIdTeacherQuery = entityManager.createQuery("select t from Teacher t" +
                " left join fetch t.faculty" +
                " left join fetch t.lectures l" +
                " left join fetch l.group g" +
                " left join fetch g.faculty" +
                " left join fetch l.audience" +
                " left join fetch l.lesson le" +
                " left join fetch le.subject where t.id =:id");
        findByIdTeacherQuery.setParameter("id", id);
        return (Teacher) findByIdTeacherQuery.getSingleResult();
    }

    @Override
    public List<Teacher> findAll() {
        return entityManager.createQuery("select t from Teacher t").getResultList();
    }

    @Override
    public boolean update(Teacher teacher) {
        Query updateTeacherQuery = entityManager.createQuery("update Teacher set firstName=:firstName, lastName=:lastName," +
                " middleName=:middleName where id =: id");
        updateTeacherQuery.setParameter("firstName", teacher.getFirstName());
        updateTeacherQuery.setParameter("lastName", teacher.getLastName());
        updateTeacherQuery.setParameter("middleName", teacher.getMiddleName());
        updateTeacherQuery.setParameter("id", teacher.getId());
        return updateTeacherQuery.executeUpdate() != 0;
    }

    @Override
    public boolean deleteById(Long id) {
        Query deleteTeacherQuery = entityManager.createQuery("delete Teacher where id =: id");
        deleteTeacherQuery.setParameter("id", id);
        return deleteTeacherQuery.executeUpdate() != 0;
    }
}
