package com.company.schedule.management.system.controller;

import com.company.schedule.management.system.controller.web.SubjectController;
import com.company.schedule.management.system.controller.web.util.DurationFormatter;
import com.company.schedule.management.system.model.Subject;
import com.company.schedule.management.system.service.LessonService;
import com.company.schedule.management.system.service.SubjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubjectController.class)
class SubjectControllerTest {

    @MockBean
    SubjectService subjectService;
    @MockBean
    LessonService lessonService;
    @MockBean
    DurationFormatter durationFormatter;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void addSubject_shouldAddSubject_whenInputCorrectData() throws Exception {
        Subject subjectWithId = new Subject(1L, "math", null);
        Subject subjectWithoutId = new Subject("math", null);

        when(subjectService.saveSubject(subjectWithoutId)).thenReturn(subjectWithId);
        mockMvc.perform(
                post("/subjects/add")
                        .flashAttr("subject", subjectWithoutId))
                .andExpect(redirectedUrl("/subjects"))
                .andExpect(view().name("redirect:/subjects"));

        verify(subjectService, times(1)).saveSubject(subjectWithoutId);
    }

    @Test
    void getAllSubjects_shouldShowFormWithAllSubjects() throws Exception {
        List<Subject> allSubjects = List.of(
                new Subject(1L, "math", null),
                new Subject(2L, "art", null));

        when(subjectService.getAllSubjects()).thenReturn(allSubjects);

        mockMvc.perform(get("/subjects"))
                .andExpect(status().isOk())
                .andExpect(view().name("subjects"))
                .andExpect(model().attribute("subjects", allSubjects))
                .andExpect(model().attribute("subject", new Subject()));

        verify(subjectService, times(1)).getAllSubjects();
    }

    @Test
    void updateSubject_shouldUpdateSubject() throws Exception {
        Subject subject = new Subject(1L, "math", null);
        when(subjectService.updateSubject(subject)).thenReturn(subject);
        mockMvc.perform(
                post("/subjects/update/{id}", 1L)
                        .flashAttr("subject", subject))
                .andExpect(redirectedUrl("/subjects"))
                .andExpect(view().name("redirect:/subjects"));

        verify(subjectService, times(1)).updateSubject(subject);
    }

    @Test
    void deleteSubject_shouldDeleteSubject() throws Exception {
        Subject subject = new Subject(1L, "math", null);
        doNothing().when(subjectService).deleteSubjectById(1L);
        mockMvc.perform(
                get("/subjects/delete/{id}", 1L)
                        .flashAttr("subject", subject))
                .andExpect(redirectedUrl("/subjects"))
                .andExpect(view().name("redirect:/subjects"));

        verify(subjectService, times(1)).deleteSubjectById(1L);
    }
}