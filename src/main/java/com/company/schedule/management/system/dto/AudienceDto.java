package com.company.schedule.management.system.dto;

import com.company.schedule.management.system.model.Lecture;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AudienceDto {

    private Long id;
    private Integer number;
    private Integer capacity;
    private List<Lecture> lectures;
}
