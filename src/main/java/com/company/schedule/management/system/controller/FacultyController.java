package com.company.schedule.management.system.controller;

import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.service.FacultyService;
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

@Controller
@RequiredArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;

    @PostMapping("/faculties/add")
    public String addFaculty(@Valid @ModelAttribute Faculty faculty, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("fieldErrors", bindingResult.getFieldErrors());
            return "redirect:/faculties";
        }
        try {
            facultyService.saveFaculty(faculty);
        } catch (ServiceException cause) {
            redirectAttributes.addFlashAttribute("faculty", new Faculty());
            redirectAttributes.addFlashAttribute("serviceExceptionOnAdd", cause);
            return "redirect:/faculties";
        }
        return "redirect:/faculties";
    }

    @GetMapping("/faculties/{id}")
    public String getFacultyById(@PathVariable("id") Long id, Model model) {
        Faculty faculty = facultyService.getFacultyById(id);
        model.addAttribute("faculty", faculty);

        model.addAttribute("groups", faculty.getGroups());
        model.addAttribute("teachers", faculty.getTeachers());

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
    public String updateFaculty(@Valid @ModelAttribute Faculty faculty, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("fieldErrors", bindingResult.getFieldErrors());
            return "redirect:/faculties/edit/{id}";
        }
        try {
            facultyService.saveFaculty(faculty);
        } catch (ServiceException cause) {
            redirectAttributes.addFlashAttribute("faculty", new Faculty());
            redirectAttributes.addFlashAttribute("serviceExceptionOnUpdate", cause);
            return "redirect:/faculties/edit/{id}";
        }
        return "redirect:/faculties";
    }

    @GetMapping(path = "/faculties/delete/{id}")
    public String deleteFaculty(@PathVariable("id") Long id) {
        facultyService.deleteFacultyById(id);
        return "redirect:/faculties";
    }
}
