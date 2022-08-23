package com.company.schedule.management.system.model.formatter;

import com.company.schedule.management.system.model.Audience;
import com.company.schedule.management.system.service.AudienceService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class AudienceFormatter implements Formatter<Audience> {

    private final AudienceService audienceService;

    public AudienceFormatter(AudienceService audienceService) {
        this.audienceService = audienceService;
    }

    @Override
    public Audience parse(String text, Locale locale) throws ParseException {
        return audienceService.getAudienceById(Long.valueOf(text));
    }

    @Override
    public String print(Audience audience, Locale locale) {
        return audience.toString();
    }
}
