package com.company.schedule.management.system.dto;

import com.company.schedule.management.system.model.Lecture;
import lombok.*;

import java.util.List;

@Data
public class AudienceDTO {

    private Long id;
    private Integer number;
    private Integer capacity;
    private List<Lecture> lectures;
}
