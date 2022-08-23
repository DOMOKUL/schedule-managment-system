package com.company.schedule.management.system.repository;

import com.company.schedule.management.system.model.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    @Override
    @Query("select l from Lecture l left join fetch l.group g left join fetch g.faculty left join fetch" +
            " l.audience left join fetch l.lesson le left join fetch le.subject left join fetch" +
            " l.teacher t left join t.faculty where l.id =:id")
    Optional<Lecture> findById(@Param("id") Long id);

    @Override
    @Query("select l from Lecture l left join fetch l.group g left join fetch g.faculty left join fetch" +
            " l.audience left join fetch l.lesson le left join fetch le.subject left join fetch" +
            " l.teacher t left join fetch t.faculty")
    List<Lecture> findAll();
}
