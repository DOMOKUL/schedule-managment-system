package com.company.schedule.management.system.service;

import com.company.schedule.management.system.model.Audience;
import com.company.schedule.management.system.model.Lecture;

import java.util.List;
import java.util.Optional;

public interface AudienceService {

    Audience saveAudience(Audience audience);

    Optional<Audience> getAudienceById(Long id);

    List<Audience> getAllAudiences();

    boolean updateAudience(Audience audience);

    boolean deleteAudienceById(Long id);

    List<Audience> saveAllAudiences(List<Audience> audiences);

    boolean addLectureToAudience(Lecture lecture, Audience audience);

    boolean removeLectureFromAudience(Lecture lecture, Audience audience);
}
