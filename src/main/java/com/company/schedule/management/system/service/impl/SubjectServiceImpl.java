package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.model.Subject;
import com.company.schedule.management.system.repository.SubjectRepository;
import com.company.schedule.management.system.service.SubjectService;
import com.company.schedule.management.system.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectServiceImpl.class);
    private final SubjectRepository subjectRepository;

    @Override
    public Subject saveSubject(Subject subject) {
        try {
            return subjectRepository.saveAndFlush(subject);
        } catch (DataIntegrityViolationException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public Subject getSubjectById(Long id) {
        LOGGER.debug("Subject at id = {} found: {}", id, subjectRepository.findById(id).get());
        return subjectRepository.findById(id).orElseThrow(() -> new ServiceException("Subject with id: " + id + " doesn't exist"));
    }

    @Override
    public List<Subject> getAllSubjects() {
        LOGGER.debug("Subjects found:{}", subjectRepository.findAll());
        return subjectRepository.findAll();
    }

    @Override
    public Subject updateSubject(Subject subject) {
        LOGGER.debug("Subject has been updated: {}", subject);
        try {
            return subjectRepository.saveAndFlush(subject);
        } catch (DataIntegrityViolationException cause) {
            throw new ServiceException(cause);
        }
    }

    @Override
    public void deleteSubjectById(Long id) {
        if (subjectRepository.findById(id).isPresent()) {
            subjectRepository.deleteById(id);
            LOGGER.debug("Subject with id: {} has been deleted", id);
        } else {
            throw new ServiceException("Audience with id: " + id + " doesn't exist");
        }

    }
}
