package com.company.schedule.management.system.controller;

import com.company.schedule.management.system.model.*;
import com.company.schedule.management.system.service.AudienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AudienceController {

    private final AudienceService audienceService;

    @PostMapping("/audiences/add")
    public String addAudience(Model model, @ModelAttribute("audience") Audience audience) {
        if (audience.getNumber() != 0 && audience.getNumber() != null) {
            audienceService.saveAudience(audience);
            return "redirect:/audiences";
        }
        model.addAttribute("errorMessage", "Audience not created");
        return "redirect:/audiences";
    }

    @GetMapping("/audiences/{id}")
    public String getAudienceById(@PathVariable("id") Long id, Model model) {
        Audience audience = audienceService.getAudienceById(id);
        model.addAttribute("audience", audience);

        model.addAttribute("lectures", audience.getLectures());

        model.addAttribute("lecture", new Lecture());
        model.addAttribute("allAudiences", audienceService.getAllAudiences());
        return "audience";
    }

    @GetMapping("/audiences")
    public String getAllAudiences(Model model) {
        model.addAttribute("audiences", audienceService.getAllAudiences());
        model.addAttribute("audience", new Audience());
        return "audiences";
    }

    @GetMapping("/audiences/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Audience audience = audienceService.getAudienceById(id);
        model.addAttribute("updatedAudience", audience);

        return "update_audience";
    }

    @PostMapping(path = "/audiences/update/{id}")
    public String updateAudience(@ModelAttribute Audience audience) {
        audienceService.updateAudience(audience);
        return "redirect:/audiences";
    }

    @GetMapping(path = "/audiences/delete/{id}")
    public String deleteAudience(@PathVariable("id") Long id) {
        audienceService.deleteAudienceById(id);
        return "redirect:/audiences";
    }
}
