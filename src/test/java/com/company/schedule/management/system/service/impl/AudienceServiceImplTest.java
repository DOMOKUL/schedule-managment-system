package com.company.schedule.management.system.service.impl;

import com.company.schedule.management.system.dao.AudienceDao;
import com.company.schedule.management.system.model.Audience;
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
    void saveAudience() {
        when(audienceDao.create(audienceWithoutId)).thenReturn(audienceWithId);
        audienceServiceImpl.saveAudience(audienceWithoutId);
        verify(audienceDao, times(1)).create(audienceWithoutId);
    }

    @Test
    void getAudienceById() {
        when(audienceDao.findById(1L)).thenReturn(Optional.ofNullable(audienceWithId));

        Audience testAudience = audienceServiceImpl.getAudienceById(1L);
        assertNotNull(testAudience);
        assertEquals(10, testAudience.getNumber());
    }

    @Test
    void getAllAudiences() {
        when(audienceDao.findAll()).thenReturn(audienceList);
        List<Audience> audiences = audienceServiceImpl.getAllAudiences();

        assertEquals(audienceList, audiences);
        verify(audienceDao, times(1)).findAll();
    }

    @Test
    void updateAudience() {
        Audience expected = new Audience(1L, 100, 128, null);

        when(audienceDao.update(expected)).thenReturn(expected);
        Audience actual = audienceServiceImpl.updateAudience(expected);

        assertEquals(expected, actual);
        verify(audienceDao, times(1)).update(expected);
    }

    @Test
    void deleteAudienceById() {
        when(audienceDao.deleteById(audienceWithId.getId())).thenReturn(true);

        assertTrue(audienceServiceImpl.deleteAudienceById(audienceWithId.getId()));

        verify(audienceDao, times(1)).deleteById(audienceWithId.getId());
    }
}