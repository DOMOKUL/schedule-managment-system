package com.company.schedule.management.system.repository;

import com.company.schedule.management.system.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Override
    @Query("select s from Student s left join fetch s.group g left join fetch g.faculty where s.id = :id")
    Optional<Student> findById(@Param("id") Long id);
}
