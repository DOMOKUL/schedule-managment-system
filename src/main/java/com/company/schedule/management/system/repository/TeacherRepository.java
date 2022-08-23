package com.company.schedule.management.system.repository;

import com.company.schedule.management.system.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Override
    @Query("select t from Teacher t left join fetch t.faculty left join fetch t.lectures l left join fetch" +
            " l.group g left join fetch g.faculty left join fetch l.audience left join fetch" +
            " l.lesson le left join fetch le.subject where t.id =:id")
    Optional<Teacher> findById(@Param("id") Long id);
}
