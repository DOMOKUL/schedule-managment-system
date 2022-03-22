package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.SubjectDao;
import com.company.schedule.management.system.model.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SubjectDaoImpl implements SubjectDao {

    private final EntityManager entityManager;

    @Override
    public Subject create(Subject subject) {
        entityManager.merge(subject);
        return subject;
    }

    @Override
    public Subject findById(Long id) {
        Query findByIdSubjectQuery = entityManager.createQuery("select s from Subject s" +
                " left join fetch s.lessons  where s.id = :id");
        findByIdSubjectQuery.setParameter("id", id);
        return (Subject) findByIdSubjectQuery.getSingleResult();
    }

    @Override
    public List<Subject> findAll() {
        return entityManager.createQuery("select s from Subject s").getResultList();
    }

    @Override
    public boolean update(Subject subject) {
        Query updateSubjectQuery = entityManager.createQuery("update Subject set name=:name where id =: id");
        updateSubjectQuery.setParameter("name", subject.getName());
        updateSubjectQuery.setParameter("id", subject.getId());
        return updateSubjectQuery.executeUpdate() != 0;
    }

    public boolean deleteById(Long id) {
        Query deleteSubjectQuery = entityManager.createQuery("delete Subject where id =: id");
        deleteSubjectQuery.setParameter("id", id);
        return deleteSubjectQuery.executeUpdate() != 0;
    }
}
