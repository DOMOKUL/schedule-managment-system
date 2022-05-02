package com.company.schedule.management.system.controller;

import com.company.schedule.management.system.controller.util.DurationFormatter;
import com.company.schedule.management.system.model.*;
import com.company.schedule.management.system.service.FacultyService;
import com.company.schedule.management.system.service.GroupService;
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

@WebMvcTest(GroupController.class)
class GroupControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    GroupService groupService;
    @MockBean
    FacultyService facultyService;
    @MockBean
    DurationFormatter durationFormatter;

    @Test
    void addGroup_shouldAddGroup_whenInputCorrectData() throws Exception {
        Group groupWithId = new Group(1L, "BSBO", null, null, null);
        Group groupWithoutId = new Group("BSBO", null, null, null);

        when(groupService.saveGroup(groupWithoutId)).thenReturn(groupWithId);
        mockMvc.perform(
                post("/groups/add")
                        .flashAttr("group", groupWithoutId))
                .andExpect(redirectedUrl("/groups"))
                .andExpect(view().name("redirect:/groups"));

        verify(groupService, times(1)).saveGroup(groupWithoutId);
    }

    @Test
    void getGroupById_shouldShowFormForOneGroup() throws Exception {
        when(durationFormatter.print(Duration.ofMinutes(90), Locale.getDefault())).thenReturn("1:30");

        Group group = new Group(1L, "BSBO", null, null, null);

        Faculty faculty = new Faculty(1L, "IT", null, null);

        group.setFaculty(faculty);

        List<Lecture> lectures = List.of(new Lecture(1L, 1, Date.valueOf(LocalDate.of(2022, 4, 27)), null, group, null, null),
                new Lecture(2L, 2, Date.valueOf(LocalDate.of(2022, 4, 28)), null, group, null, null));

        group.setLectures(lectures);

        when(groupService.getGroupById(1L)).thenReturn(group);

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

        List<Audience> audiences = List.of(new Audience(1L, 10, 10, lectures), new Audience(2L, 20, 20, lectures));
        for (int i = 0; i < lectures.size(); i++) {
            lectures.get(i).setAudience(audiences.get(i));
        }

        List<Group> groups = List.of(new Group(1L, "BSBO", faculty, null, lectures),
                new Group(2L, "BABO", faculty, null, lectures));

        for (int i = 0; i < lectures.size(); i++) {
            lectures.get(i).setGroup(groups.get(i));
        }
        List<Student> students = List.of(new Student(1L, 1, group), new Student(2L, 2, group));
        group.setStudents(students);

        List<Group> allGroups = List.of(new Group(1L, "NANO", faculty, students, lectures),
                new Group(2L, "NINU", faculty, students, lectures),
                new Group(3L, "NUNE", faculty, students, lectures));

        when(groupService.getAllGroups()).thenReturn(allGroups);

        mockMvc.perform(get("/groups/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(model().attribute("group", group))
                .andExpect(model().attribute("lectures", group.getLectures()))
                .andExpect(model().attribute("allGroups", allGroups))
                .andExpect(model().attribute("students", group.getStudents()))
                .andExpect(view().name("group"));

        verify(groupService, times(1)).getGroupById(1L);
        verify(groupService, times(1)).getAllGroups();
    }

    @Test
    void getAllGroups_shouldFetchAllGroups() throws Exception {
        Faculty faculty = new Faculty(4L, "CHT", null, null);

        List<Group> groups = List.of(
                new Group(1L, "MIMI", faculty, null, null),
                new Group(2L, "MOMO", faculty, null, null));

        when(groupService.getAllGroups()).thenReturn(groups);

        List<Faculty> faculties = List.of(
                new Faculty(1L, "IT", null, null),
                new Faculty(2L, "INTEGU", null, null),
                new Faculty(3L, "IKBSP", null, null)
        );
        when(facultyService.getAllFaculties()).thenReturn(faculties);

        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andExpect(view().name("groups"))
                .andExpect(model().attribute("groups", groups))
                .andExpect(model().attribute("group", new Group()))
                .andExpect(model().attribute("allFaculties", faculties));

        verify(groupService, times(1)).getAllGroups();
        verify(facultyService, times(1)).getAllFaculties();
    }

    @Test
    public void updateGroup_shouldUpdateGroup() throws Exception {
        Group group = new Group(1L, "BSBO", null, null, null);
        when(groupService.updateGroup(group)).thenReturn(group);
        mockMvc.perform(
                post("/groups/update/{id}", 1L)
                        .flashAttr("group", group))
                .andExpect(redirectedUrl("/groups"))
                .andExpect(view().name("redirect:/groups"));

        verify(groupService, times(1)).updateGroup(group);
    }

    @Test
    public void deleteGroup_shouldDeleteGroup() throws Exception {
        Group group = new Group(1L, "BSBO", null, null, null);
        doNothing().when(groupService).deleteGroupById(1L);
        mockMvc.perform(
                get("/groups/delete/{id}", 1L)
                        .flashAttr("group", group))
                .andExpect(redirectedUrl("/groups"))
                .andExpect(view().name("redirect:/groups"));

        verify(groupService, times(1)).deleteGroupById(1L);
    }
}