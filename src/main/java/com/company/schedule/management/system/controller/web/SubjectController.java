package com.company.schedule.management.system.controller.web;

import com.company.schedule.management.system.controller.web.util.StringUtils;
import com.company.schedule.management.system.model.Subject;
import com.company.schedule.management.system.service.LessonService;
import com.company.schedule.management.system.service.SubjectService;
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
public class SubjectController {

    private final SubjectService subjectService;
    private final LessonService lessonService;

    @PostMapping("/subjects/add")
    public String addSubject(@Valid @ModelAttribute Subject subject, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("fieldErrors", bindingResult.getFieldErrors());
            return "redirect:/subjects";
        }
        try {
            subjectService.saveSubject(subject);
        } catch (ServiceException cause) {
            redirectAttributes.addFlashAttribute("subject", new Subject());
            redirectAttributes.addFlashAttribute("serviceExceptionOnAdd", cause);
            return "redirect:/subjects";
        }
        return "redirect:/subjects";
    }

    @GetMapping("/subjects/{id}")
    public String getSubjectById(@PathVariable("id") Long id, Model model) {
        Subject subject = subjectService.getSubjectById(id);
        model.addAttribute("subject", subject);

        model.addAttribute("lessons", subject.getLessons());
        model.addAttribute("durations", StringUtils.formatListOfDurations(lessonService.getDurationsForLesson(lessonService.getAllLessons())));

        model.addAttribute("allSubjects", subjectService.getAllSubjects());
        return "subject";
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
    public String updateSubject(@Valid @ModelAttribute Subject subject, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("fieldErrors", bindingResult.getFieldErrors());
            return "redirect:/subjects/edit/{id}";
        }
        try {
            subjectService.updateSubject(subject);
        } catch (ServiceException cause) {
            redirectAttributes.addFlashAttribute("subject", new Subject());
            redirectAttributes.addFlashAttribute("serviceExceptionOnAdd", cause);
            return "redirect:/subjects/edit/{id}";
        }
        return "redirect:/subjects";
    }

    @GetMapping(path = "/subjects/delete/{id}")
    public String deleteSubject(@PathVariable("id") Long id) {
        subjectService.deleteSubjectById(id);
        return "redirect:/subjects";
    }
}
