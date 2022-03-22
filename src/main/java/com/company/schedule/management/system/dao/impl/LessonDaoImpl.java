package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.LessonDao;
import com.company.schedule.management.system.model.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LessonDaoImpl implements LessonDao {

    private final EntityManager entityManager;

    @Override
    public Lesson create(Lesson lesson) {
        entityManager.merge(lesson);
        return lesson;
    }

    @Override
    public Lesson findById(Long id) {
        Query findByIdLessonQuery = entityManager.createQuery("select l from Lesson l" +
                " left join fetch l.subject s" +
                " left join fetch s.lessons where l.id = :id");
        findByIdLessonQuery.setParameter("id", id);
        return (Lesson) findByIdLessonQuery.getSingleResult();
    }

    @Override
    public List<Lesson> findAll() {
        return entityManager.createQuery("select l from Lesson l").getResultList();
    }

    @Override
    public boolean update(Lesson lesson) {
        Query updateLessonQuery = entityManager.createQuery("update Lesson set number=:number, startTime=:startTime," +
                " duration=:duration where id =: id");
        updateLessonQuery.setParameter("number", lesson.getNumber());
        updateLessonQuery.setParameter("startTime", lesson.getStartTime());
        updateLessonQuery.setParameter("duration", lesson.getDuration());
        updateLessonQuery.setParameter("id", lesson.getId());
        return updateLessonQuery.executeUpdate() != 0;
    }

    @Override
    public boolean deleteById(Long id) {
        Query deleteLessonQuery = entityManager.createQuery("delete Lesson where id =: id");
        deleteLessonQuery.setParameter("id", id);
        return deleteLessonQuery.executeUpdate() != 0;
    }
}
