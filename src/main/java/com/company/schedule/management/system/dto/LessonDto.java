package com.company.schedule.management.system.dto;

import com.company.schedule.management.system.model.Lecture;
import com.company.schedule.management.system.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.Duration;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LessonDto {

    private Long id;
    private Integer number;
    private Time startTime;
    private Duration duration;
    private Subject subject;
    private List<Lecture> lectures;
}
