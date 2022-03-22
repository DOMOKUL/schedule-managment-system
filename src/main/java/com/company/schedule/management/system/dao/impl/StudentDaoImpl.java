package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.StudentDao;
import com.company.schedule.management.system.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudentDaoImpl implements StudentDao {

    private final EntityManager entityManager;

    @Override
    public Student create(Student student) {
        entityManager.merge(student);
        return student;
    }

    @Override
    public Student findById(Long id) {
        Query findByIdStudentQuery = entityManager.createQuery("select s from Student s" +
                " left join fetch s.group g" +
                " left join fetch g.faculty where s.id = :id");
        findByIdStudentQuery.setParameter("id", id);
        return (Student) findByIdStudentQuery.getSingleResult();
    }

    @Override
    public List<Student> findAll() {
        return entityManager.createQuery("select s from Student s").getResultList();
    }

    @Override
    public boolean update(Student student) {
        Query updateStudentQuery = entityManager.createQuery("update Student set courseNumber=:courseNumber," +
                " firstName=:firstName, lastName=:lastName, middleName=:middleName where id =: id");
        updateStudentQuery.setParameter("courseNumber", student.getCourseNumber());
        updateStudentQuery.setParameter("firstName", student.getFirstName());
        updateStudentQuery.setParameter("lastName", student.getLastName());
        updateStudentQuery.setParameter("middleName", student.getMiddleName());
        updateStudentQuery.setParameter("id", student.getId());
        return updateStudentQuery.executeUpdate() != 0;
    }

    @Override
    public boolean deleteById(Long id) {
        Query deleteStudentQuery = entityManager.createQuery("delete Student where id =: id");
        deleteStudentQuery.setParameter("id", id);
        return deleteStudentQuery.executeUpdate() != 0;
    }
}
