package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.model.Audience;
import com.company.schedule.management.system.model.Lecture;
import com.company.schedule.management.system.service.AudienceService;

import java.util.List;
import java.util.Optional;

public class AudienceServiceImpl implements AudienceService {

    @Override
    public Audience saveAudience(Audience audience) {
        return null;
    }

    @Override
    public Optional<Audience> getAudienceById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Audience> getAllAudiences() {
        return null;
    }

    @Override
    public boolean updateAudience(Audience audience) {
        return false;
    }

    @Override
    public boolean deleteAudienceById(Long id) {
        return false;
    }

    @Override
    public List<Audience> saveAllAudiences(List<Audience> audiences) {
        return null;
    }

    @Override
    public boolean addLectureToAudience(Lecture lecture, Audience audience) {
        return false;
    }

    @Override
    public boolean removeLectureFromAudience(Lecture lecture, Audience audience) {
        return false;
    }
}
