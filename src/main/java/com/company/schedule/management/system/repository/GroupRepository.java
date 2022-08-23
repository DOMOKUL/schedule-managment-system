package com.company.schedule.management.system.repository;

import com.company.schedule.management.system.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Override
    @Query("select g from Group g left join fetch g.lectures l left join fetch l.teacher t left join fetch" +
            " t.faculty left join fetch l.lesson le left join fetch le.subject left join fetch" +
            " l.audience left join fetch g.faculty where g.id =:id")
    Optional<Group> findById(@Param("id") Long id);
}
