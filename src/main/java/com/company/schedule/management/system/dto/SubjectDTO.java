package com.company.schedule.management.system.dto;

import com.company.schedule.management.system.model.Lesson;
import lombok.*;

import java.util.List;

@Data
public class SubjectDTO {

    private Long id;
    private String name;
    private List<Lesson> lessons;
}
