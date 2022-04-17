package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.dao.AudienceDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Audience;
import com.company.schedule.management.system.service.AudienceService;
import com.company.schedule.management.system.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AudienceServiceImpl implements AudienceService {

    @Autowired
    private final AudienceDao audienceDao;

    @Override
    public Audience saveAudience(Audience audience) {
        try {
            return audienceDao.create(audience);
        } catch (DaoException cause) {
            throw new ServiceException("Audience doesn't save", cause);
        }
    }

    @Override
    public Audience getAudienceById(Long id) {
        return audienceDao.findById(id).orElseThrow(() -> new ServiceException("Audience with id:" + id + " doesn't exist"));
    }

    @Override
    public List<Audience> getAllAudiences() {
        try {
            return audienceDao.findAll();
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public Audience updateAudience(Audience audience) {
        try {
            return audienceDao.update(audience);
        } catch (DaoException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public boolean deleteAudienceById(Long id) {
        try {
            return audienceDao.deleteById(id);
        } catch (DaoException cause) {
            throw new ServiceException("Audience doesn't delete ", cause);
        }
    }
}
