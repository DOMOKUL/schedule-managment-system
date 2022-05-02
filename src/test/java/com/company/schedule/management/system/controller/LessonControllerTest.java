package com.company.schedule.management.system.controller;

import com.company.schedule.management.system.controller.util.DurationFormatter;
import com.company.schedule.management.system.controller.util.StringUtils;
import com.company.schedule.management.system.model.*;
import com.company.schedule.management.system.service.LessonService;
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

@WebMvcTest(LessonController.class)
class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LessonService lessonService;
    @MockBean
    private DurationFormatter durationFormatter;

    @Test
    void addLesson_shouldAddLesson_whenInputCorrectData() throws Exception {
        Lesson lessonWithId = new Lesson(1L, 1, Time.valueOf(LocalTime.of(9, 0)), Duration.ofMinutes(90), null, null);
        Lesson lessonWithoutId = new Lesson(1, Time.valueOf(LocalTime.of(9, 0)), Duration.ofMinutes(90), null, null);

        when(lessonService.saveLesson(lessonWithoutId)).thenReturn(lessonWithId);
        mockMvc.perform(
                post("/lessons/add")
                        .flashAttr("lesson", lessonWithoutId))
                .andExpect(redirectedUrl("/lessons"))
                .andExpect(view().name("redirect:/lessons"));

        verify(lessonService, times(1)).saveLesson(lessonWithoutId);
    }

    @Test
    void getLessonById_shouldShowFormWithOneLesson() throws Exception {
        when(durationFormatter.print(Duration.ofMinutes(90), Locale.getDefault())).thenReturn("1:30");

        Lesson lesson = new Lesson(1L, 10, Time.valueOf(LocalTime.of(9, 0)), Duration.ofMinutes(90), null, null);

        List<Lecture> lectures = List.of(new Lecture(1L, 1, Date.valueOf(LocalDate.of(2022, 4, 27)), null, null, lesson, null),
                new Lecture(2L, 2, Date.valueOf(LocalDate.of(2022, 4, 28)), null, null, lesson, null));

        lesson.setLectures(lectures);

        when(lessonService.getLessonById(1L)).thenReturn(lesson);

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

        Subject subject = new Subject(1L, "art", lessons);

        List<Lesson> allLessons = List.of(new Lesson(1L, 1, Time.valueOf(LocalTime.of(9, 0)), Duration.ofMinutes(90), subject, lectures),
                new Lesson(2L, 2, Time.valueOf(LocalTime.of(9, 0)), Duration.ofMinutes(90), subject, lectures),
                new Lesson(3L, 3, Time.valueOf(LocalTime.of(9, 0)), Duration.ofMinutes(90), subject, lectures));

        when(lessonService.getAllLessons()).thenReturn(allLessons);

        mockMvc.perform(get("/lessons/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(model().attribute("lesson", lesson))
                .andExpect(model().attribute("duration", StringUtils.formatDuration(lesson.getDuration())))
                .andExpect(model().attribute("lectures", lesson.getLectures()))
                .andExpect(model().attribute("allLessons", allLessons))
                .andExpect(view().name("lesson"));

        verify(lessonService, times(1)).getLessonById(1L);
        verify(lessonService, times(1)).getAllLessons();
    }

    @Test
    void updateLesson_shouldUpdateLesson() throws Exception {
        Lesson lesson = new Lesson(1L, 1, Time.valueOf(LocalTime.of(8, 30, 0)), Duration.ofMinutes(90), null, null);
        when(lessonService.updateLesson(lesson)).thenReturn(lesson);
        mockMvc.perform(
                post("/lessons/update/{id}", 1L)
                        .flashAttr("lesson", lesson))
                .andExpect(redirectedUrl("/lessons"))
                .andExpect(view().name("redirect:/lessons"));

        verify(lessonService, times(1)).updateLesson(lesson);
    }

    @Test
    void deleteLesson_shouldDeleteLesson() throws Exception {
        Lesson lesson = new Lesson(1L, 1, Time.valueOf(LocalTime.of(8, 30, 0)), Duration.ofMinutes(90), null, null);
        doNothing().when(lessonService).deleteLessonById(1L);
        mockMvc.perform(
                get("/lessons/delete/{id}", 1L)
                        .flashAttr("lesson", lesson))
                .andExpect(redirectedUrl("/lessons"))
                .andExpect(view().name("redirect:/lessons"));

        verify(lessonService, times(1)).deleteLessonById(1L);
    }
}