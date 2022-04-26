package com.company.schedule.management.system.controller;

import com.company.schedule.management.system.model.Audience;
import com.company.schedule.management.system.service.AudienceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AudienceController.class)
class AudienceControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AudienceService audienceService;

    private List<Audience> audienceList;

    @BeforeEach
    void setUp() {
        this.audienceList = new ArrayList<>();
        this.audienceList.add(new Audience(1L, 1, 1, null));
        this.audienceList.add(new Audience(2L, 2, 2, null));
        this.audienceList.add(new Audience(3L, 3, 3, null));
    }

    @Test
    void addAudience_shouldAddAudience_whenInputCorrectData() throws Exception {
        Audience audience = new Audience(1L, 10, 10, null);
        when(audienceService.saveAudience(new Audience(10, 10, null))).thenReturn(audience);
        mockMvc.perform(
                post("/audiences/add")
                        .flashAttr("audience", audience))
                .andExpect(redirectedUrl("/audiences"))
                .andExpect(view().name("redirect:/audiences"));

        verify(audienceService, times(1)).saveAudience(new Audience(201, 25, null));

    }

    @Test
    void getAudienceById() {
    }

    @Test
    void getAllAudiences_shouldFetchAllAudiences() {
    }

    @Test
    void showUpdateForm() {
    }

    @Test
    void updateAudience() {
    }

    @Test
    void deleteAudience() {
    }
}