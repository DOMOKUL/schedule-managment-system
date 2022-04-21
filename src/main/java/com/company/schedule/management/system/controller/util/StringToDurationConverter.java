package com.company.schedule.management.system.controller.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class StringToDurationConverter implements Converter<String, Duration> {

    @Override
    public Duration convert(String source) {
        return Duration.ofMinutes(Long.parseLong(source));
    }
}
