package com.company.schedule.management.system.controller;

import com.company.schedule.management.system.controller.util.DurationFormatter;
import com.company.schedule.management.system.controller.util.StringUtils;
import com.company.schedule.management.system.model.Group;
import com.company.schedule.management.system.model.Lecture;
import com.company.schedule.management.system.model.Lesson;
import com.company.schedule.management.system.model.Student;
import com.company.schedule.management.system.service.LessonService;
import com.company.schedule.management.system.service.SubjectService;
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
public class LessonController {

    private final LessonService lessonService;
    private final SubjectService subjectService;

    @PostMapping("/lessons/add")
    public String addLesson(@ModelAttribute Lesson lesson) {
        lessonService.saveLesson(lesson);
        return "redirect:/lessons";
    }

    @GetMapping("/lessons/{id}")
    public String getLessonById(@PathVariable("id") Long id, Model model) {
        Lesson lesson = lessonService.getLessonById(id);
        model.addAttribute("lesson", lesson);
        model.addAttribute("duration", StringUtils.formatDuration(lesson.getDuration()));

        model.addAttribute("lectures", lesson.getLectures());

        model.addAttribute("lecture", new Lecture());
        model.addAttribute("allGroups", lessonService.getAllLessons());
        return "lesson";
    }

    @GetMapping("/lessons")
    public String getAllLessons(Model model) {
        List<Lesson> allLessons = lessonService.getAllLessons();
        model.addAttribute("lessons", allLessons);
        model.addAttribute("durations", StringUtils.formatListOfDurations(lessonService.getDurationsForLesson(allLessons)));
        model.addAttribute("lesson", new Lesson());
        model.addAttribute("allSubjects", subjectService.getAllSubjects());
        return "lessons";
    }

    @GetMapping("/lessons/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Lesson lesson = lessonService.getLessonById(id);
        model.addAttribute("updatedLesson", lesson);
        model.addAttribute("allSubjects", subjectService.getAllSubjects());
        return "update_lesson";
    }

    @PostMapping("/lessons/update/{id}")
    public String updateLesson(@ModelAttribute Lesson lesson) {
        lessonService.updateLesson(lesson);
        return "redirect:/lessons";
    }

    @GetMapping("/lessons/delete/{id}")
    public String deleteLesson(@PathVariable("id") Long id) {
        lessonService.deleteLessonById(id);
        return "redirect:/lessons";
    }
}