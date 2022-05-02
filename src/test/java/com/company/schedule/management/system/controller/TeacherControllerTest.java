package com.company.schedule.management.system.controller;

import com.company.schedule.management.system.controller.util.DurationFormatter;
import com.company.schedule.management.system.model.*;
import com.company.schedule.management.system.service.FacultyService;
import com.company.schedule.management.system.service.TeacherService;
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

@WebMvcTest(TeacherController.class)
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TeacherService teacherService;
    @MockBean
    private FacultyService facultyService;
    @MockBean
    private DurationFormatter durationFormatter;

    @Test
    void addTeacher_shouldAddTeacher_whenInputCorrectData() throws Exception {
        Teacher teacherWithId = new Teacher(1L, null, null);
        Teacher teacherWithoutId = new Teacher(null, null);

        when(teacherService.saveTeacher(teacherWithoutId)).thenReturn(teacherWithId);
        mockMvc.perform(
                post("/teachers/add")
                        .flashAttr("teacher", teacherWithoutId))
                .andExpect(redirectedUrl("/teachers"))
                .andExpect(view().name("redirect:/teachers"));

        verify(teacherService, times(1)).saveTeacher(teacherWithoutId);
    }

    @Test
    void getTeacherById_shouldShowFormWithOneTeacher() throws Exception {
        when(durationFormatter.print(Duration.ofMinutes(90), Locale.getDefault())).thenReturn("1:30:00");
        Teacher teacher = new Teacher(1L, null, null);
        when(teacherService.getTeacherById(1L)).thenReturn(teacher);

        Faculty faculty = new Faculty(1L, "IT", null, null);
        teacher.setFaculty(faculty);
        faculty.setTeachers(List.of(teacher));

        List<Lecture> lectures = List.of(
                new Lecture(1L, 1, Date.valueOf(LocalDate.of(2022, 5, 1)), null, null, null, teacher),
                new Lecture(2L, 2, Date.valueOf(LocalDate.of(2022, 5, 1)), null, null, null, teacher));
        teacher.setLectures(lectures);

        List<Lesson> lessons = List.of(
                new Lesson(1L, 1, Time.valueOf(LocalTime.of(10, 40, 0)), Duration.ofMinutes(90), null, lectures),
                new Lesson(2L, 2, Time.valueOf(LocalTime.of(9, 0, 0)), Duration.ofMinutes(90), null, lectures));

        List<Audience> audiences = List.of(
                new Audience(1L, 10, 45, lectures),
                new Audience(2L, 20, 55, lectures));

        for (int i = 0; i < lectures.size(); i++) {
            lectures.get(0).setAudience(audiences.get(0));
        }

        List<Subject> subjects = List.of(
                new Subject(1L, "math", null),
                new Subject(2L, "art", null));

        for (int i = 0; i < lectures.size(); i++) {
            subjects.get(0).setLessons(lessons);
        }

        List<Group> groups = List.of(
                new Group(1L, "BSBO", faculty, null, lectures),
                new Group(2L, "BASO", faculty, null, lectures));

        for (int i = 0; i < lectures.size(); i++) {
            lectures.get(0).setGroup(groups.get(0));
        }

        List<Teacher> allTeachers = List.of(
                new Teacher(1L, faculty, lectures),
                new Teacher(2L, faculty, lectures),
                new Teacher(3L, faculty, lectures));
        when(teacherService.getAllTeachers()).thenReturn(allTeachers);

        mockMvc.perform(get("/teachers/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("teacher"))
                .andExpect(model().attribute("lectures", lectures))
                .andExpect(model().attribute("teacher", teacher))
                .andExpect(model().attribute("allTeachers", allTeachers));

        verify(teacherService, times(1)).getTeacherById(1L);
        verify(teacherService, times(1)).getAllTeachers();
    }

    @Test
    void getAllTeachers_shouldShowFormWithAllTeachers() throws Exception {
        List<Teacher> allTeachers = List.of(
                new Teacher(1L, null, null),
                new Teacher(2L, null, null));

        when(teacherService.getAllTeachers()).thenReturn(allTeachers);

        List<Faculty> allFaculties = List.of(
                new Faculty(1L, "IT", null, null),
                new Faculty(2L, "INTEGU", null, null),
                new Faculty(3L, "IKBSP", null, null));
        when(facultyService.getAllFaculties()).thenReturn(allFaculties);

        mockMvc.perform(get("/teachers"))
                .andExpect(status().isOk())
                .andExpect(view().name("teachers"))
                .andExpect(model().attribute("teachers", allTeachers))
                .andExpect(model().attribute("allFaculties", allFaculties))
                .andExpect(model().attribute("teacher", new Teacher()));

        verify(teacherService, times(1)).getAllTeachers();
        verify(facultyService, times(1)).getAllFaculties();
    }

    @Test
    void updateTeacher_shouldUpdateTeacher() throws Exception {
        Teacher teacher = new Teacher(1L, null, null);
        when(teacherService.updateTeacher(teacher)).thenReturn(teacher);
        mockMvc.perform(
                post("/teachers/update/{id}", 1L)
                        .flashAttr("teacher", teacher))
                .andExpect(redirectedUrl("/teachers"))
                .andExpect(view().name("redirect:/teachers"));

        verify(teacherService, times(1)).updateTeacher(teacher);
    }

    @Test
    void deleteTeacher_shouldDeleteTeacher() throws Exception {
        Teacher teacher = new Teacher(1L, null, null);
        doNothing().when(teacherService).deleteTeacherById(1L);
        mockMvc.perform(
                get("/teachers/delete/{id}", 1L)
                        .flashAttr("teacher", teacher))
                .andExpect(redirectedUrl("/teachers"))
                .andExpect(view().name("redirect:/teachers"));

        verify(teacherService, times(1)).deleteTeacherById(1L);
    }
}