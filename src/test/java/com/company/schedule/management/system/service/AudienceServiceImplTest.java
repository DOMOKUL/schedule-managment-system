package com.company.schedule.management.system.service;

import com.company.schedule.management.system.model.Audience;
import com.company.schedule.management.system.repository.AudienceRepository;
import com.company.schedule.management.system.service.impl.AudienceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {AudienceServiceImpl.class})
class AudienceServiceImplTest {

    @Autowired
    AudienceServiceImpl audienceServiceImpl;
    @MockBean
    AudienceRepository audienceRepository;

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
        when(audienceRepository.saveAndFlush(audienceWithoutId)).thenReturn(audienceWithId);
        audienceServiceImpl.saveAudience(audienceWithoutId);
        verify(audienceRepository, times(1)).saveAndFlush(audienceWithoutId);
    }

    @Test
    void saveAudience_shouldThrowException_whenInputExistAudience() {
        when(audienceRepository.saveAndFlush(audienceWithId)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> audienceServiceImpl.saveAudience(audienceWithId));
    }

    @Test
    void getAudienceById_shouldReturnAudience_whenInputExistId() {
        when(audienceRepository.findById(1L)).thenReturn(Optional.ofNullable(audienceWithId));

        Audience testAudience = audienceServiceImpl.getAudienceById(1L);
        assertAll(
                () -> assertNotNull(testAudience),
                () -> assertEquals(10, testAudience.getNumber())
        );
    }

    @Test
    void getAudienceById_shouldThrowException_whenInputNonExistAudience() {
        when(audienceRepository.findById(audienceWithId.getId())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> audienceServiceImpl.getAudienceById(audienceWithId.getId()));
    }

    @Test
    void getAllAudiences_shouldReturnListAudiences() {
        when(audienceRepository.findAll()).thenReturn(audienceList);
        List<Audience> audiences = audienceServiceImpl.getAllAudiences();

        assertEquals(audienceList, audiences);
        verify(audienceRepository, times(2)).findAll();
    }

    @Test
    void updateAudience_shouldReturnUpdatedAudience_whenInputCorrectAudience() {
        Audience expected = new Audience(1L, 100, 128, null);

        when(audienceRepository.saveAndFlush(expected)).thenReturn(expected);
        Audience actual = audienceServiceImpl.updateAudience(expected);

        assertEquals(expected, actual);
        verify(audienceRepository, times(1)).saveAndFlush(expected);
    }

    @Test
    void updateAudience_shouldThrowException_whenInputNonExistAudience() {
        when(audienceRepository.saveAndFlush(audienceWithId)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> audienceServiceImpl.updateAudience(audienceWithId));
    }

    @Test
    void deleteAudienceById_shouldReturnTrue_whenInputExistAudienceId() {
        when(audienceRepository.findById(audienceWithId.getId())).thenReturn(Optional.of(audienceWithId));

        audienceServiceImpl.deleteAudienceById(audienceWithId.getId());

        verify(audienceRepository, times(1)).deleteById(audienceWithId.getId());
    }
}