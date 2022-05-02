package com.company.schedule.management.system.service;

import com.company.schedule.management.system.dao.AudienceDao;
import com.company.schedule.management.system.dao.exception.DaoException;
import com.company.schedule.management.system.model.Audience;
import com.company.schedule.management.system.service.exception.ServiceException;
import com.company.schedule.management.system.service.impl.AudienceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {AudienceServiceImpl.class})
class AudienceServiceImplTest {

    @Autowired
    AudienceServiceImpl audienceServiceImpl;
    @MockBean
    AudienceDao audienceDao;

    private Audience audienceWithId;
    private Audience audienceWithoutId;
    private List<Audience> audienceList;

    @BeforeEach
    void setUp() {
        audienceWithId = new Audience(1L, 10, 10, null);
        audienceWithoutId = new Audience(10, 10, null);
        audienceList = List.of(audienceWithId);
    }

    @Test
    void saveAudience_shouldReturnAudience_whenInputCorrectValue() {
        when(audienceDao.create(audienceWithoutId)).thenReturn(audienceWithId);
        audienceServiceImpl.saveAudience(audienceWithoutId);
        verify(audienceDao, times(1)).create(audienceWithoutId);
    }

    @Test
    void saveAudience_shouldThrowException_whenInputExistAudience() {
        when(audienceDao.create(audienceWithId)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> audienceServiceImpl.saveAudience(audienceWithId));
    }

    @Test
    void getAudienceById_shouldReturnAudience_whenInputExistId() {
        when(audienceDao.findById(1L)).thenReturn(Optional.ofNullable(audienceWithId));

        Audience testAudience = audienceServiceImpl.getAudienceById(1L);
        assertAll(
                () -> assertNotNull(testAudience),
                () -> assertEquals(10, testAudience.getNumber())
        );
    }

    @Test
    void getAudienceById_shouldThrowException_whenInputNonExistAudience() {
        when(audienceDao.findById(audienceWithId.getId())).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> audienceServiceImpl.getAudienceById(audienceWithId.getId()));
        //TODO странная штука
    }

    @Test
    void getAllAudiences_shouldReturnListAudiences() {
        when(audienceDao.findAll()).thenReturn(audienceList);
        List<Audience> audiences = audienceServiceImpl.getAllAudiences();

        assertEquals(audienceList, audiences);
        verify(audienceDao, times(1)).findAll();
    }

    @Test
    void updateAudience_shouldReturnUpdatedAudience_whenInputCorrectAudience() {
        Audience expected = new Audience(1L, 100, 128, null);

        when(audienceDao.update(expected)).thenReturn(expected);
        Audience actual = audienceServiceImpl.updateAudience(expected);

        assertEquals(expected, actual);
        verify(audienceDao, times(1)).update(expected);
    }

    @Test
    void updateAudience_shouldThrowException_whenInputNonExistAudience() {
        when(audienceDao.update(audienceWithId)).thenThrow(DaoException.class);
        assertThrows(ServiceException.class, () -> audienceServiceImpl.updateAudience(audienceWithId));
    }

    @Test
    void deleteAudienceById_shouldReturnTrue_whenInputExistAudienceId() {
        when(audienceDao.deleteById(audienceWithId.getId())).thenReturn(true);

       audienceServiceImpl.deleteAudienceById(audienceWithId.getId());

        verify(audienceDao, times(1)).deleteById(audienceWithId.getId());
    }

    @Test
    void deleteAudienceById_shouldThrowException_whenInputNonExistAudienceId() {
        when(audienceDao.deleteById(audienceWithId.getId())).thenThrow(DaoException.class);

        assertThrows(ServiceException.class, () -> audienceServiceImpl.deleteAudienceById(audienceWithId.getId()));
    }
}