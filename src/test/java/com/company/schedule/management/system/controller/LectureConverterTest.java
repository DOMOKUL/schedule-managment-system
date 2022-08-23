package com.company.schedule.management.system.controller;

import com.company.schedule.management.system.controller.web.LectureController;
import com.company.schedule.management.system.controller.web.util.DurationFormatter;
import com.company.schedule.management.system.model.*;
import com.company.schedule.management.system.service.*;
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

@WebMvcTest(LectureController.class)
class LectureConverterTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    LectureService lectureService;
    @MockBean
    TeacherService teacherService;
    @MockBean
    GroupService groupService;
    @MockBean
    LessonService lessonService;
    @MockBean
    AudienceService audienceService;
    @MockBean
    DurationFormatter durationFormatter;

    @Test
    void addLecture_shouldAddLecture_whenInputCorrectData() throws Exception {
        Lecture lectureWithId = new Lecture(1L, 1, Date.valueOf(LocalDate.of(2022, 4, 30)), null, null, null, null);
        Lecture lectureWithoutId = new Lecture(1, Date.valueOf(LocalDate.of(2022, 4, 30)), null, null, null, null);

        when(lectureService.saveLecture(lectureWithoutId)).thenReturn(lectureWithId);
        mockMvc.perform(
                post("/lectures/add")
                        .flashAttr("lecture", lectureWithoutId))
                .andExpect(redirectedUrl("/lectures"))
                .andExpect(view().name("redirect:/lectures"));

        verify(lectureService, times(1)).saveLecture(lectureWithoutId);
    }

    @Test
    void getAllLectures_shouldFetchAllLectures() throws Exception {
        when(durationFormatter.print(Duration.ofMinutes(90), Locale.getDefault())).thenReturn("1:30:00");

        Faculty faculty = new Faculty(1L, "IT", null, null);

        List<Lecture> lectures = List.of(
                new Lecture(1L, 1, Date.valueOf(LocalDate.of(2022, 4, 30)), null, null, null, null),
                new Lecture(2L, 2, Date.valueOf(LocalDate.of(2022, 4, 30)), null, null, null, null));
        when(lectureService.getAllLectures()).thenReturn(lectures);

        List<Audience> audiences = List.of(new Audience(1L, 10, 10, lectures), new Audience(2L, 20, 20, lectures));
        for (int i = 0; i < lectures.size(); i++) {
            lectures.get(i).setAudience(audiences.get(i));
        }

        List<Group> groups = List.of(new Group(1L, "BSBO", faculty, null, lectures),
                new Group(2L, "BABO", faculty, null, lectures));

        for (int i = 0; i < lectures.size(); i++) {
            lectures.get(i).setGroup(groups.get(i));
        }

        List<Lesson> lessons = List.of(new Lesson(1L, 1, Time.valueOf(LocalTime.of(9, 0)), Duration.ofMinutes(90), null, lectures),
                new Lesson(2L, 2, Time.valueOf(LocalTime.of(9, 0, 0)), Duration.ofMinutes(90), null, lectures));

        for (int i = 0; i < lectures.size(); i++) {
            lectures.get(i).setLesson(lessons.get(i));
        }

        List<Subject> subjects = List.of(new Subject(1L, "math", lessons), new Subject(2L, "art", lessons));

        for (int i = 0; i < lessons.size(); i++) {
            lessons.get(i).setSubject(subjects.get(i));
        }

        List<Teacher> teachers = List.of(new Teacher(1L, faculty, lectures), new Teacher(2L, faculty, lectures));

        for (int i = 0; i < lectures.size(); i++) {
            lectures.get(i).setTeacher(teachers.get(i));
        }

        List<Teacher> allTeachers = List.of(
                new Teacher(1L, null, null),
                new Teacher(2L, null, null),
                new Teacher(3L, null, null),
                new Teacher(4L, null, null));
        when(teacherService.getAllTeachers()).thenReturn(allTeachers);

        List<Audience> allAudiences = List.of(
                new Audience(1L, 301, 45, null),
                new Audience(2L, 302, 55, null),
                new Audience(3L, 303, 65, null),
                new Audience(4L, 304, 45, null));
        when(audienceService.getAllAudiences()).thenReturn(allAudiences);

        List<Group> allGroups = List.of(
                new Group(1L, "AB-01", null, null, null),
                new Group(2L, "AB-11", null, null, null),
                new Group(3L, "CD-21", null, null, null));
        when(groupService.getAllGroups()).thenReturn(allGroups);

        List<Lesson> allLessons = List.of(
                new Lesson(1L, 1, Time.valueOf(LocalTime.of(8, 30, 0)), Duration.ofMinutes(90), null, null),
                new Lesson(2L, 2, Time.valueOf(LocalTime.of(10, 10, 0)), Duration.ofMinutes(90), null, null),
                new Lesson(3L, 2, Time.valueOf(LocalTime.of(10, 10, 0)), Duration.ofMinutes(90), null, null));
        when(lessonService.getAllLessons()).thenReturn(allLessons);

        mockMvc.perform(get("/lectures"))
                .andExpect(status().isOk())
                .andExpect(view().name("lectures"))
                .andExpect(model().attribute("lectures", lectures))
                .andExpect(model().attribute("lecture", new Lecture()))
                .andExpect(model().attribute("allTeachers", allTeachers))
                .andExpect(model().attribute("allAudiences", allAudiences))
                .andExpect(model().attribute("allGroups", allGroups))
                .andExpect(model().attribute("allLessons", allLessons));

        verify(lectureService, times(1)).getAllLectures();
        verify(teacherService, times(1)).getAllTeachers();
        verify(audienceService, times(1)).getAllAudiences();
        verify(groupService, times(1)).getAllGroups();
        verify(lessonService, times(1)).getAllLessons();
    }

    @Test
    public void updateLecture_shouldUpdateLecture() throws Exception {
        Lecture lecture = new Lecture(1L, 1, Date.valueOf(LocalDate.of(2022, 5, 2)), null, null, null, null);
        when(lectureService.updateLecture(lecture)).thenReturn(lecture);
        mockMvc.perform(
                post("/lectures/update/{id}", 1L)
                        .flashAttr("lecture", lecture))
                .andExpect(redirectedUrl("/lectures"))
                .andExpect(view().name("redirect:/lectures"));

        verify(lectureService, times(1)).updateLecture(lecture);
    }

    @Test
    public void deleteLecture_shouldDeleteLecture() throws Exception {
        Lecture lecture = new Lecture(1L, 1, Date.valueOf(LocalDate.of(2022, 5, 2)), null, null, null, null);
        doNothing().when(lectureService).deleteLectureById(1L);
        mockMvc.perform(
                get("/lectures/delete/{id}", 1L)
                        .flashAttr("lecture", lecture))
                .andExpect(redirectedUrl("/lectures"))
                .andExpect(view().name("redirect:/lectures"));

        verify(lectureService, times(1)).deleteLectureById(1L);
    }
}