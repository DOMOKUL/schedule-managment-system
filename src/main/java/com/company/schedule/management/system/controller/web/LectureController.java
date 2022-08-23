package com.company.schedule.management.system.controller.web;

import com.company.schedule.management.system.model.Lecture;
import com.company.schedule.management.system.service.*;
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
public class LectureController {

    private final LectureService lectureService;
    private final AudienceService audienceService;
    private final LessonService lessonService;
    private final TeacherService teacherService;
    private final GroupService groupService;

    @PostMapping("/lectures/add")
    public String addLecture(@Valid @ModelAttribute Lecture lecture, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("fieldErrors", bindingResult.getFieldErrors());
            return "redirect:/lectures";
        }
        try {
            lectureService.saveLecture(lecture);
        } catch (ServiceException cause) {
            redirectAttributes.addFlashAttribute("lecture", new Lecture());
            redirectAttributes.addFlashAttribute("serviceExceptionOnAdd", cause);
            return "redirect:/lectures";
        }
        return "redirect:/lectures";
    }

    @GetMapping("/lectures")
    public String getAllLectures(Model model) {
        List<Lecture> allLectures = lectureService.getAllLectures();
        model.addAttribute("lectures", allLectures);
        model.addAttribute("lecture", new Lecture());
        model.addAttribute("allAudiences", audienceService.getAllAudiences());
        model.addAttribute("allGroups", groupService.getAllGroups());
        model.addAttribute("allLessons", lessonService.getAllLessons());
        model.addAttribute("allTeachers", teacherService.getAllTeachers());
        return "lectures";
    }

    @GetMapping("/lectures/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Lecture lecture = lectureService.getLectureById(id);
        model.addAttribute("updatedLecture", lecture);
        model.addAttribute("allAudiences", audienceService.getAllAudiences());
        model.addAttribute("allGroups", groupService.getAllGroups());
        model.addAttribute("allLessons", lessonService.getAllLessons());
        model.addAttribute("allTeachers", teacherService.getAllTeachers());
        return "update_lecture";
    }

    @PostMapping("/lectures/update/{id}")
    public String updateLecture(@Valid @ModelAttribute Lecture lecture, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("fieldErrors", bindingResult.getFieldErrors());
            return "redirect:/lectures/edit/{id}";
        }
        try {
            lectureService.updateLecture(lecture);
        } catch (ServiceException cause) {
            redirectAttributes.addFlashAttribute("lecture", new Lecture());
            redirectAttributes.addFlashAttribute("serviceExceptionOnAdd", cause);
            return "redirect:/lectures/edit/{id}";
        }
        return "redirect:/lectures";
    }

    @GetMapping("/lectures/delete/{id}")
    public String deleteLecture(@PathVariable("id") Long id) {
        lectureService.deleteLectureById(id);
        return "redirect:/lectures";
    }
}
