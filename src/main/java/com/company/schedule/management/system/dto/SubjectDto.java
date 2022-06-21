package com.company.schedule.management.system.dto;

import com.company.schedule.management.system.model.Lesson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubjectDto {

    private Long id;
    private String name;
    private List<Lesson> lessons;
}
