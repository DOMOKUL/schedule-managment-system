package com.company.schedule.management.system.dto;

import com.company.schedule.management.system.model.Lecture;
import com.company.schedule.management.system.model.Subject;
import lombok.*;

import java.sql.Time;
import java.time.Duration;
import java.util.List;

@Data
public class LessonDTO {

    private Long id;
    private Integer number;
    private Time startTime;
    private Duration duration;
    private Subject subject;
    private List<Lecture> lectures;
}
