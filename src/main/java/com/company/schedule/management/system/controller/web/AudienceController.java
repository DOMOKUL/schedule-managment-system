package com.company.schedule.management.system.controller.web;

import com.company.schedule.management.system.model.Audience;
import com.company.schedule.management.system.service.AudienceService;
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
public class AudienceController {

    private final AudienceService audienceService;

    @PostMapping("/audiences/add")
    public String addAudience(@Valid @ModelAttribute Audience audience, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("fieldErrors", bindingResult.getFieldErrors());
            return "redirect:/audiences";
        }
        try {
            audienceService.saveAudience(audience);
        } catch (ServiceException cause) {
            redirectAttributes.addFlashAttribute("audience", new Audience());
            redirectAttributes.addFlashAttribute("serviceExceptionOnAdd", cause);
            return "redirect:/audiences";
        }
        return "redirect:/audiences";
    }

    @GetMapping("/audiences/{id}")
    public String getAudienceById(@PathVariable("id") Long id, Model model) {
        Audience audience = audienceService.getAudienceById(id);
        model.addAttribute("audience", audience);

        model.addAttribute("lectures", audience.getLectures());

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
    public String updateAudience(@Valid @ModelAttribute Audience audience, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("fieldErrors", bindingResult.getFieldErrors());
            return "redirect:/audiences/edit/{id}";
        }
        try {
            audienceService.updateAudience(audience);
        } catch (ServiceException cause) {
            redirectAttributes.addFlashAttribute("audience", new Audience());
            redirectAttributes.addFlashAttribute("serviceExceptionOnUpdate", cause);
            return "redirect:/audiences/edit/{id}";
        }
        return "redirect:/audiences";
    }

    @GetMapping(path = "/audiences/delete/{id}")
    public String deleteAudience(@PathVariable("id") Long id) {
        audienceService.deleteAudienceById(id);
        return "redirect:/audiences";
    }
}
