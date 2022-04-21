package com.company.schedule.management.system.controller.util;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.text.ParseException;
import java.util.Locale;

@Component
public class TimeFormatter implements Formatter<Time> {
    @Override
    public Time parse(String text, Locale locale) throws ParseException {
        if (text.length() <= 5) {
            text += ":00";
        }
        return Time.valueOf(text);
    }

    @Override
    public String print(Time time, Locale locale) {
        return String.valueOf(time.toLocalTime());
    }
}
