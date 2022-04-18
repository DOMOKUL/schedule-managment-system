package com.company.schedule.management.system.controller;

import com.company.schedule.management.system.model.Audience;
import com.company.schedule.management.system.service.AudienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AudienceController {

    @Autowired
    private AudienceService audienceService;

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
    public String getAudienceById(@PathVariable("id") Long id) {
        audienceService.getAudienceById(id);
        return "audiences";
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
