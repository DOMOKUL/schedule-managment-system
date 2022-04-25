package com.company.schedule.management.system.controller;

import com.company.schedule.management.system.model.*;
import com.company.schedule.management.system.service.FacultyService;
import com.company.schedule.management.system.service.GroupService;
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
public class GroupController {

    private final GroupService groupService;
    private final FacultyService facultyService;

    @PostMapping("/groups/add")
    public String addGroup(@ModelAttribute Group group) {
        groupService.saveGroup(group);
        return "redirect:/groups";
    }

    @GetMapping("/groups/{id}")
    public String getGroupById(@PathVariable("id") Long id, Model model) {
        Group group = groupService.getGroupById(id);
        model.addAttribute("group", group);

        model.addAttribute("students", group.getStudents());
        model.addAttribute("lectures", group.getLectures());

        model.addAttribute("student", new Student());
        model.addAttribute("lecture", new Lecture());
        model.addAttribute("allGroups", groupService.getAllGroups());
        return "group";
    }

    @GetMapping("/groups")
    public String getAllGroups(Model model) {
        List<Group> allGroups = groupService.getAllGroups();
        model.addAttribute("groups", allGroups);
        model.addAttribute("group", new Group());
        model.addAttribute("allFaculties", facultyService.getAllFaculties());
        return "groups";
    }

    @GetMapping("/groups/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Group group = groupService.getGroupById(id);
        model.addAttribute("updatedGroup", group);
        model.addAttribute("allFaculties", facultyService.getAllFaculties());
        return "update_group";
    }

    @PostMapping(path = "/groups/update/{id}")
    public String updateGroup(@ModelAttribute Group group) {
        groupService.updateGroup(group);
        return "redirect:/groups";
    }

    @GetMapping(path = "/groups/delete/{id}")
    public String deleteGroup(@PathVariable("id") Long id) {
        groupService.deleteGroupById(id);
        return "redirect:/groups";
    }
}
