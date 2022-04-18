package com.company.schedule.management.system.controller.util;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Locale;

@Component
public class DateFormatter implements Formatter<Date> {
    @Override
    public Date parse(String text, Locale locale) {
        if (text.isEmpty()) return new Date(0);
        return Date.valueOf(text);
    }

    @Override
    public String print(Date date, Locale locale) {
        return date.toString();
    }
}
