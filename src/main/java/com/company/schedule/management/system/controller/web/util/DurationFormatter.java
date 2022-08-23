package com.company.schedule.management.system.controller.web.util;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Duration;
import java.util.Locale;

@Component
public class DurationFormatter implements Formatter<Duration> {

    @Override
    public Duration parse(String text, Locale locale) throws ParseException {
        return Duration.ofMinutes(Long.parseLong(text));
    }

    @Override
    public String print(Duration duration, Locale locale) {
        long seconds = duration.getSeconds();
        return String.format("%d:%02d", seconds / 3600, (seconds % 3600) / 60);
    }
}
