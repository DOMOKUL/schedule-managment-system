package com.company.schedule.management.system.controller;

import com.company.schedule.management.system.model.Audience;
import com.company.schedule.management.system.model.Subject;
import com.company.schedule.management.system.service.AudienceService;
import com.company.schedule.management.system.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping("/subjects/add")
    public String addSubject(Model model, @ModelAttribute("subject") Subject subject) {
        if (subject.getName() != null) {
            subjectService.saveSubject(subject);
            return "redirect:/subjects";
        }
        model.addAttribute("errorMessage", "Audience not created");
        return "redirect:/subjects";
    }

    @GetMapping("/subjects/{id}")
    public String getSubjectById(@PathVariable("id") Long id) {
        subjectService.getSubjectById(id);
        return "subjects";
    }

    @GetMapping("/subjects")
    public String getAllSubjects(Model model) {
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("subject", new Subject());
        return "subjects";
    }

    @GetMapping("/subjects/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Subject subject = subjectService.getSubjectById(id);
        model.addAttribute("updatedSubject", subject);

        return "update_subject";
    }

    @PostMapping(path = "/subjects/update/{id}")
    public String updateSubject(@ModelAttribute Subject subject) {
        subjectService.updateSubject(subject);
        return "redirect:/subjects";
    }

    @GetMapping(path = "/subjects/delete/{id}")
    public String deleteSubject(@PathVariable("id") Long id) {
        subjectService.deleteSubjectById(id);
        return "redirect:/subjects";
    }
}
