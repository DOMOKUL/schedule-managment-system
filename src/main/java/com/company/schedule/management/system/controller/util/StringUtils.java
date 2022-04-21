package com.company.schedule.management.system.controller.util;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class StringUtils {
    private static final DurationFormatter durationFormatter = new DurationFormatter();

    public static List<String> formatListOfDurations(List<Duration> durations) {
        return durations.stream()
                .map(duration -> durationFormatter.print(duration, Locale.getDefault()))
                .collect(Collectors.toList());
    }
}

