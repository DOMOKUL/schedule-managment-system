package com.company.schedule.management.system.repository;

import com.company.schedule.management.system.model.Audience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AudienceRepository extends JpaRepository<Audience, Long> {

    @Override
    @Query("select a from Audience a left join fetch a.lectures l left join fetch l.group g left join fetch" +
            " g.faculty left join fetch l.teacher t left join fetch t.faculty left join fetch l.lesson le left join fetch" +
            " le.subject where a.id =:id")
    Optional<Audience> findById(@Param("id") Long id);
}
