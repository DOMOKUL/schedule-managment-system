package com.company.schedule.management.system.service;

import com.company.schedule.management.system.model.Audience;

import java.util.List;

public interface AudienceService {

    Audience saveAudience(Audience audience);

    Audience getAudienceById(Long id);

    List<Audience> getAllAudiences();

    Audience updateAudience(Audience audience);

    boolean deleteAudienceById(Long id);

    List<Audience> saveAllAudiences(List<Audience> audiences);

}
