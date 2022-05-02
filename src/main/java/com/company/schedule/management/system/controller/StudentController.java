package com.company.schedule.management.system.controller;

import com.company.schedule.management.system.model.Student;
import com.company.schedule.management.system.service.GroupService;
import com.company.schedule.management.system.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final GroupService groupService;

    @PostMapping("/students/add")
    public String addStudent(@ModelAttribute Student student) {
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/students")
    public String getAllStudents(Model model) {
        List<Student> allStudents = studentService.getAllStudents();
        model.addAttribute("students", allStudents);
        model.addAttribute("student", new Student());
        model.addAttribute("allGroups", groupService.getAllGroups());
        return "students";
    }

    @GetMapping("/students/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("updatedStudent", student);
        model.addAttribute("allGroups", groupService.getAllGroups());
        return "update_student";
    }

    @PostMapping("/students/update/{id}")
    public String updateStudent(@ModelAttribute Student student) {
        studentService.updateStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudentById(id);
        return "redirect:/students";
    }
}