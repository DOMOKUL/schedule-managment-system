package com.company.schedule.management.system.dto;

import com.company.schedule.management.system.model.Audience;
import com.company.schedule.management.system.model.Group;
import com.company.schedule.management.system.model.Lesson;
import com.company.schedule.management.system.model.Teacher;
import lombok.*;

import java.sql.Date;

@Data
public class LectureDTO {

    private Long id;
    private Integer number;
    private Date date;

    private Audience audience;
    private Group group;
    private Lesson lesson;
    private Teacher teacher;
}
