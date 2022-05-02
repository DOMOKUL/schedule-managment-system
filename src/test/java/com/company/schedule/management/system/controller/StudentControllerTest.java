package com.company.schedule.management.system.controller;

import com.company.schedule.management.system.model.Group;
import com.company.schedule.management.system.model.Student;
import com.company.schedule.management.system.service.GroupService;
import com.company.schedule.management.system.service.StudentService;
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

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentService studentService;
    @MockBean
    private GroupService groupService;

    @Test
    void addStudent_shouldAddStudent_whenInputCorrectData() throws Exception {
        Student studentWithId = new Student(1L, 1, null);
        Student studentWithoutId = new Student(1, null);

        when(studentService.saveStudent(studentWithoutId)).thenReturn(studentWithId);
        mockMvc.perform(
                post("/students/add")
                        .flashAttr("student", studentWithoutId))
                .andExpect(redirectedUrl("/students"))
                .andExpect(view().name("redirect:/students"));

        verify(studentService, times(1)).saveStudent(studentWithoutId);
    }

    @Test
    void getAllStudents_shouldShowFormWithAllStudents() throws Exception {
        List<Student> allStudents = List.of(
                new Student(1L, 1, null),
                new Student(2L, 2, null));

        when(studentService.getAllStudents()).thenReturn(allStudents);

        List<Group> allGroups = List.of(
                new Group(1L, "BSBO", null, null, null),
                new Group(2L, "BIBO", null, null, null),
                new Group(3L, "BABO", null, null, null));
        when(groupService.getAllGroups()).thenReturn(allGroups);

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(view().name("students"))
                .andExpect(model().attribute("students", allStudents))
                .andExpect(model().attribute("allGroups", allGroups))
                .andExpect(model().attribute("student", new Student()));

        verify(studentService, times(1)).getAllStudents();
        verify(groupService, times(1)).getAllGroups();
    }

    @Test
    void updateStudent_shouldUpdateStudent() throws Exception {
        Student student = new Student(1L, 1, null);
        when(studentService.updateStudent(student)).thenReturn(student);
        mockMvc.perform(
                post("/students/update/{id}", 1L)
                        .flashAttr("student", student))
                .andExpect(redirectedUrl("/students"))
                .andExpect(view().name("redirect:/students"));

        verify(studentService, times(1)).updateStudent(student);
    }

    @Test
    void deleteStudent_shouldDeleteStudent() throws Exception {
        Student student = new Student(1L, 1, null);
        doNothing().when(studentService).deleteStudentById(1L);
        mockMvc.perform(
                get("/students/delete/{id}", 1L)
                        .flashAttr("student", student))
                .andExpect(redirectedUrl("/students"))
                .andExpect(view().name("redirect:/students"));

        verify(studentService, times(1)).deleteStudentById(1L);
    }

}