package com.company.schedule.management.system.controller;

import com.company.schedule.management.system.controller.util.StringUtils;
import com.company.schedule.management.system.model.Lecture;
import com.company.schedule.management.system.model.Lesson;
import com.company.schedule.management.system.model.Subject;
import com.company.schedule.management.system.model.Teacher;
import com.company.schedule.management.system.service.FacultyService;
import com.company.schedule.management.system.service.TeacherService;
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
public class TeacherController {

    private final TeacherService teacherService;
    private final FacultyService facultyService;

    @PostMapping("/teachers/add")
    public String addTeacher(@ModelAttribute Teacher teacher) {
        teacherService.saveTeacher(teacher);
        return "redirect:/teachers";
    }

    @GetMapping("/teachers/{id}")
    public String getTeacherById(@PathVariable("id") Long id, Model model) {
        Teacher teacher = teacherService.getTeacherById(id);
        model.addAttribute("teacher", teacher);

        model.addAttribute("lectures", teacher.getLectures());

        model.addAttribute("lecture", new Lecture());
        model.addAttribute("allTeachers", teacherService.getAllTeachers());
        return "teacher";
    }

    @GetMapping("/teachers")
    public String getAllTeachers(Model model) {
        List<Teacher> allTeachers = teacherService.getAllTeachers();
        model.addAttribute("teachers", allTeachers);
        model.addAttribute("teacher", new Teacher());
        model.addAttribute("allFaculties", facultyService.getAllFaculties());
        return "teachers";
    }

    @GetMapping("/teachers/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Teacher teacher = teacherService.getTeacherById(id);
        model.addAttribute("updatedTeacher", teacher);
        model.addAttribute("allFaculties", facultyService.getAllFaculties());
        return "update_teacher";
    }

    @PostMapping("/teachers/update/{id}")
    public String updateTeacher(@ModelAttribute Teacher teacher) {
        teacherService.updateTeacher(teacher);
        return "redirect:/teachers";
    }

    @GetMapping("/teachers/delete/{id}")
    public String deleteTeacher(@PathVariable("id") Long id) {
        teacherService.deleteTeacherById(id);
        return "redirect:/teachers";
    }
}