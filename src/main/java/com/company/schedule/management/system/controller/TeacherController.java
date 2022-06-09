package com.company.schedule.management.system.controller;

import com.company.schedule.management.system.model.Teacher;
import com.company.schedule.management.system.service.FacultyService;
import com.company.schedule.management.system.service.TeacherService;
import com.company.schedule.management.system.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;
    private final FacultyService facultyService;

    @PostMapping("/teachers/add")
    public String addTeacher(@Valid @ModelAttribute Teacher teacher, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("fieldErrors", bindingResult.getFieldErrors());
            return "redirect:/teachers";
        }
        try {
            teacherService.saveTeacher(teacher);
        } catch (ServiceException cause) {
            redirectAttributes.addFlashAttribute("teacher", new Teacher());
            redirectAttributes.addFlashAttribute("serviceExceptionOnAdd", cause);
            return "redirect:/teachers";
        }
        return "redirect:/teachers";
    }

    @GetMapping("/teachers/{id}")
    public String getTeacherById(@PathVariable("id") Long id, Model model) {
        Teacher teacher = teacherService.getTeacherById(id);
        model.addAttribute("teacher", teacher);

        model.addAttribute("lectures", teacher.getLectures());

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
    public String updateTeacher(@Valid @ModelAttribute Teacher teacher, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("fieldErrors", bindingResult.getFieldErrors());
            return "redirect:/teachers/edit/{id}";
        }
        try {
            teacherService.updateTeacher(teacher);
        } catch (ServiceException cause) {
            redirectAttributes.addFlashAttribute("teacher", new Teacher());
            redirectAttributes.addFlashAttribute("serviceExceptionOnAdd", cause);
            return "redirect:/teachers/edit/{id}";
        }
        return "redirect:/teachers";
    }

    @GetMapping("/teachers/delete/{id}")
    public String deleteTeacher(@PathVariable("id") Long id) {
        teacherService.deleteTeacherById(id);
        return "redirect:/teachers";
    }
}