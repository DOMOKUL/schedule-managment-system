package com.company.schedule.management.system.controller;

import com.company.schedule.management.system.controller.web.FacultyController;
import com.company.schedule.management.system.controller.web.util.DurationFormatter;
import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.model.Group;
import com.company.schedule.management.system.model.Teacher;
import com.company.schedule.management.system.service.FacultyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FacultyService facultyService;
    @MockBean
    private DurationFormatter durationFormatter;

    @Test
    void addFaculty_shouldAddFaculty_whenInputCorrectData() throws Exception {
        Faculty facultyWithId = new Faculty(1L, "IT", null, null);
        Faculty facultyWithoutId = new Faculty("IT", null, null);

        when(facultyService.saveFaculty(facultyWithoutId)).thenReturn(facultyWithId);
        mockMvc.perform(
                post("/faculties/add")
                        .flashAttr("faculty", facultyWithoutId))
                .andExpect(redirectedUrl("/faculties"))
                .andExpect(view().name("redirect:/faculties"));

        verify(facultyService, times(1)).saveFaculty(facultyWithoutId);
    }

    @Test
    void getFacultyById_shouldShowFormForOneFaculty() throws Exception {
        when(durationFormatter.print(Duration.ofMinutes(90), Locale.getDefault())).thenReturn("1:30");

        Faculty faculty = new Faculty(1L, "IT", null, null);

        List<Group> groups = List.of(new Group(1L, "BSBO", faculty, null, null),
                new Group(2L, "BIBA", faculty, null, null));

        faculty.setGroups(groups);

        List<Teacher> teachers = List.of(new Teacher(1L, faculty, null),
                new Teacher(2L, faculty, null));

        faculty.setTeachers(teachers);

        when(facultyService.getFacultyById(1L)).thenReturn(faculty);

        List<Faculty> allFaculties = List.of(new Faculty(1L, "IT", groups, teachers),
                new Faculty(2L, "INTEGU", groups, teachers),
                new Faculty(3L, "IKBSP", groups, teachers));

        when(facultyService.getAllFaculties()).thenReturn(allFaculties);

        mockMvc.perform(get("/faculties/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(model().attribute("faculty", faculty))
                .andExpect(model().attribute("teachers", faculty.getTeachers()))
                .andExpect(model().attribute("groups", faculty.getGroups()))
                .andExpect(model().attribute("allFaculties", allFaculties))
                .andExpect(view().name("faculty"));

        verify(facultyService, times(1)).getFacultyById(1L);
        verify(facultyService, times(1)).getAllFaculties();
    }

    @Test
    void getAllFaculties_shouldFetchAllFaculties() throws Exception {
        List<Faculty> faculties = List.of(new Faculty(1L, "IT", null, null),
                new Faculty(1L, "BSBO", null, null));

        when(facultyService.getAllFaculties()).thenReturn(faculties);

        mockMvc.perform(get("/faculties"))
                .andExpect(status().isOk())
                .andExpect(view().name("faculties"))
                .andExpect(model().attribute("faculties", faculties))
                .andExpect(model().attribute("faculty", new Faculty()));

        verify(facultyService, times(1)).getAllFaculties();
    }

    @Test
    void updateFaculty_shouldUpdateFaculty() throws Exception {
        Faculty faculty = new Faculty(1L, "IT", null, null);
        when(facultyService.updateFaculty(faculty)).thenReturn(faculty);
        mockMvc.perform(post("/faculties/update/{id}", 1L)
                .flashAttr("faculty", faculty))
                .andExpect(redirectedUrl("/faculties"))
                .andExpect(view().name("redirect:/faculties"));
    }

    @Test
    void deleteFaculty_shouldDeleteFaculty_whenInputExistId() throws Exception {
        Faculty faculty = new Faculty(1L, "IT", null, null);
        doNothing().when(facultyService).deleteFacultyById(1L);
        mockMvc.perform(get("/faculties/delete/{id}", 1L)
                .flashAttr("faculty", faculty))
                .andExpect(redirectedUrl("/faculties"))
                .andExpect(view().name("redirect:/faculties"));

        verify(facultyService, times(1)).deleteFacultyById(1L);
    }
}