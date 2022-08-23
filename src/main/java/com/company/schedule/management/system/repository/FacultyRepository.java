package com.company.schedule.management.system.repository;

import com.company.schedule.management.system.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    @Override
    @Query("select f from Faculty f left join fetch f.groups where f.id = :id")
    Optional<Faculty> findById(@Param("id") Long id);
}
