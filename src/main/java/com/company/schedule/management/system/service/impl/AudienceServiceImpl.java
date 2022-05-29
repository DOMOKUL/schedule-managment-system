package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.model.Audience;
import com.company.schedule.management.system.repository.AudienceRepository;
import com.company.schedule.management.system.service.AudienceService;
import com.company.schedule.management.system.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AudienceServiceImpl implements AudienceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AudienceServiceImpl.class);
    private final AudienceRepository audienceRepository;

    @Override
    public Audience saveAudience(Audience audience) {
        try {
            return audienceRepository.saveAndFlush(audience);
        } catch (DataIntegrityViolationException cause) {
            throw new ServiceException("Audience doesn't save", cause);
        }
    }

    @Override
    public Audience getAudienceById(Long id) {
        Optional<Audience> audience = audienceRepository.findById(id);
        LOGGER.debug("Audience at id = {} found: {}", id, audience.get());
        return audience.orElseThrow(() -> new ServiceException("Audience with id:" + id + " doesn't exist"));
    }

    @Override
    public List<Audience> getAllAudiences() {
        LOGGER.debug("Audiences found:{}", audienceRepository.findAll());
        return audienceRepository.findAll();
    }

    @Override
    public Audience updateAudience(Audience audience) {
        LOGGER.debug("Audience has been updated: {}", audience);
        try {
            return audienceRepository.saveAndFlush(audience);
        } catch (DataIntegrityViolationException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public void deleteAudienceById(Long id) {
        if (audienceRepository.findById(id).isPresent()) {
            audienceRepository.deleteById(id);
            LOGGER.debug("Audience with id: {} has been deleted", id);
        } else {
            throw new ServiceException("Audience with id: " + id + " doesn't exist");
        }

    }
}
