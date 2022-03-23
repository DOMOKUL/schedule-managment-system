package com.company.schedule.management.system.dao.impl;

import com.company.schedule.management.system.dao.LectureDao;
import com.company.schedule.management.system.model.Audience;
import com.company.schedule.management.system.model.Lecture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LectureDaoImpl implements LectureDao {

    private final EntityManager entityManager;

    @Override
    public Lecture create(Lecture lecture) {
        entityManager.merge(lecture);
        return lecture;
    }

    @Override
    public Lecture findById(Long id) {
        Query findByIdLectureQuery = entityManager.createQuery("select l from Lecture l" +
                " left join l.group g left join g.faculty left join l.audience" +
                " left join l.lesson le left join le.subject left join l.teacher" +
                " t left join t.faculty where l.id =:id");
        findByIdLectureQuery.setParameter("id", id);
        return (Lecture) findByIdLectureQuery.getSingleResult();
    }

    @Override
    public List<Lecture> findAll() {
        return entityManager.createQuery("select l from Lecture l" +
                " left join fetch l.group g" +
                " left join fetch g.faculty" +
                " left join fetch l.audience" +
                " left join fetch l.lesson le" +
                " left join fetch le.subject" +
                " left join fetch l.teacher" +
                " t left join fetch t.faculty").getResultList();
    }

    @Override
    public boolean update(Lecture lecture) {
        Query updateLectureQuery = entityManager.createQuery("update Lecture set date=:date, number=:number where id =: id");
        updateLectureQuery.setParameter("date", lecture.getDate());
        updateLectureQuery.setParameter("number", lecture.getNumber());
        updateLectureQuery.setParameter("id", lecture.getId());
        return updateLectureQuery.executeUpdate() != 0;
    }

    @Override
    public boolean deleteById(Long id) {
        Query deleteLectureQuery = entityManager.createQuery("delete Lecture where id =: id");
        deleteLectureQuery.setParameter("id", id);
        return deleteLectureQuery.executeUpdate() != 0;
    }
}
