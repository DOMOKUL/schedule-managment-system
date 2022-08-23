package com.company.schedule.management.system.repository;

import com.company.schedule.management.system.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Override
    @Query("select s from Subject s left join fetch s.lessons  where s.id = :id")
    Optional<Subject> findById(@Param("id") Long id);
}
