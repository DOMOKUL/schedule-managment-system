package com.company.schedule.management.system.repository;

import com.company.schedule.management.system.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Override
    @Query("select l from Lesson l left join fetch l.subject s left join fetch s.lessons where l.id = :id")
    Optional<Lesson> findById(@Param("id") Long id);
}
