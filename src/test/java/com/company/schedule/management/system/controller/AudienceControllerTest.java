package com.company.schedule.management.system.controller;

import com.company.schedule.management.system.controller.web.AudienceController;
import com.company.schedule.management.system.controller.web.util.DurationFormatter;
import com.company.schedule.management.system.model.*;
import com.company.schedule.management.system.service.AudienceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AudienceController.class)
class AudienceControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AudienceService audienceService;
    @MockBean
    private DurationFormatter durationFormatter;

    @Test
    void addAudience_shouldAddAudience_whenInputCorrectData() throws Exception {
        Audience audienceWithId = new Audience(1L, 10, 10, null);
        Audience audienceWithoutId = new Audience(10, 10, null);

        when(audienceService.saveAudience(audienceWithoutId)).thenReturn(audienceWithId);
        mockMvc.perform(
                post("/audiences/add")
                        .flashAttr("audience", audienceWithoutId))
                .andExpect(redirectedUrl("/audiences"))
                .andExpect(view().name("redirect:/audiences"));

        verify(audienceService, times(1)).saveAudience(audienceWithoutId);
    }

    @Test
    void getAudienceById_shouldShowFormForOneAudience() throws Exception {
        when(durationFormatter.print(Duration.ofMinutes(90), Locale.getDefault())).thenReturn("1:30");

        Audience audience = new Audience(1L, 10, 10, null);

        List<Lecture> lectures = List.of(new Lecture(1L, 1, Date.valueOf(LocalDate.of(2022, 4, 27)), audience, null, null, null),
                new Lecture(2L, 2, Date.valueOf(LocalDate.of(2022, 4, 28)), audience, null, null, null));

        audience.setLectures(lectures);

        when(audienceService.getAudienceById(1L)).thenReturn(audience);

        List<Lesson> lessons = List.of(new Lesson(1L, 1, Time.valueOf(LocalTime.of(9, 0)), Duration.ofMinutes(90), null, lectures),
                new Lesson(2L, 2, Time.valueOf(LocalTime.of(9, 0, 0)), Duration.ofMinutes(90), null, lectures));

        for (int i = 0; i < lectures.size(); i++) {
            lectures.get(i).setLesson(lessons.get(i));
        }

        List<Subject> subjects = List.of(new Subject(1L, "math", lessons), new Subject(2L, "art", lessons));

        for (int i = 0; i < lessons.size(); i++) {
            lessons.get(i).setSubject(subjects.get(i));
        }

        List<Teacher> teachers = List.of(new Teacher(1L, null, lectures), new Teacher(2L, null, lectures));

        for (int i = 0; i < lectures.size(); i++) {
            lectures.get(i).setTeacher(teachers.get(i));
        }

        List<Audience> audiences = List.of(new Audience(1L, 10, 10, lectures), new Audience(2L, 20, 20, lectures));
        for (int i = 0; i < lectures.size(); i++) {
            lectures.get(i).setAudience(audiences.get(i));
        }

        List<Group> groups = List.of(new Group(1L, "BSBO", null, null, lectures),
                new Group(2L, "BABO", null, null, lectures));

        for (int i = 0; i < lectures.size(); i++) {
            lectures.get(i).setGroup(groups.get(i));
        }

        List<Audience> allAudiences = List.of(new Audience(1L, 11, 11, lectures),
                new Audience(2L, 12, 12, lectures),
                new Audience(3L, 12, 12, lectures));

        when(audienceService.getAllAudiences()).thenReturn(allAudiences);

        mockMvc.perform(get("/audiences/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(model().attribute("audience", audience))
                .andExpect(model().attribute("lectures", audience.getLectures()))
                .andExpect(model().attribute("allAudiences", allAudiences))
                .andExpect(view().name("audience"));

        verify(audienceService, times(1)).getAudienceById(1L);
        verify(audienceService, times(1)).getAllAudiences();
    }

    @Test
    void getAllAudiences_shouldFetchAllAudiences() throws Exception {
        List<Audience> audiences = List.of(new Audience(1L, 1, 1, null),
                new Audience(2L, 2, 2, null));

        when(audienceService.getAllAudiences()).thenReturn(audiences);

        mockMvc.perform(get("/audiences"))
                .andExpect(status().isOk())
                .andExpect(view().name("audiences"))
                .andExpect(model().attribute("audiences", audiences))
                .andExpect(model().attribute("audience", new Audience()));

        verify(audienceService, times(1)).getAllAudiences();
    }

    @Test
    void updateAudience_shouldUpdateAudience() throws Exception {
        Audience audience = new Audience(1L, 10, 10, null);
        when(audienceService.updateAudience(audience)).thenReturn(audience);
        mockMvc.perform(post("/audiences/update/{id}", 1L)
                .flashAttr("audience", audience))
                .andExpect(redirectedUrl("/audiences"))
                .andExpect(view().name("redirect:/audiences"));
    }

    @Test
    void deleteAudience_shouldDeleteAudience_whenInputExistId() throws Exception {
        Audience audience = new Audience(1L, 10, 25, null);
        doNothing().when(audienceService).deleteAudienceById(1L);
        mockMvc.perform(get("/audiences/delete/{id}", 1L)
                .flashAttr("audience", audience))
                .andExpect(redirectedUrl("/audiences"))
                .andExpect(view().name("redirect:/audiences"));

        verify(audienceService, times(1)).deleteAudienceById(1L);
    }
}