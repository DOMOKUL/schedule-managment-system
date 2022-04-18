package com.company.schedule.management.system.controller;

import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.model.Group;
import com.company.schedule.management.system.model.Teacher;
import com.company.schedule.management.system.service.FacultyService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;

    @PostMapping("/faculties/add")
    public String addFaculty(Model model, @ModelAttribute("faculty") Faculty faculty) {
        if (faculty.getName() != null) {
            facultyService.saveFaculty(faculty);
            return "redirect:/faculties";
        }
        model.addAttribute("errorMessage", "Faculty not created");
        return "redirect:/faculties";
    }

    @GetMapping("/faculties/{id}")
    public String getFacultyById(@PathVariable("id") Long id, Model model) {
        Faculty faculty = facultyService.getFacultyById(id);
        model.addAttribute("faculty", faculty);

        model.addAttribute("groups", faculty.getGroups());
        model.addAttribute("teachers", faculty.getTeachers());

        model.addAttribute("group", new Group());
        model.addAttribute("teacher", new Teacher());
        model.addAttribute("allFaculties", facultyService.getAllFaculties());
        return "faculty";
    }

    @GetMapping("/faculties")
    public String getAllFaculties(Model model) {
        model.addAttribute("faculties", facultyService.getAllFaculties());
        model.addAttribute("faculty", new Faculty());
        return "faculties";
    }

    @GetMapping("/faculties/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Faculty faculty = facultyService.getFacultyById(id);
        model.addAttribute("updatedFaculty", faculty);

        return "update_faculty";
    }

    @PostMapping(path = "/faculties/update/{id}")
    public String updateFaculty(@ModelAttribute Faculty faculty) {
        facultyService.updateFaculty(faculty);
        return "redirect:/faculties";
    }

    @GetMapping(path = "/faculties/delete/{id}")
    public String deleteFaculty(@PathVariable("id") Long id) {
        facultyService.deleteFacultyById(id);
        return "redirect:/faculties";
    }
}
