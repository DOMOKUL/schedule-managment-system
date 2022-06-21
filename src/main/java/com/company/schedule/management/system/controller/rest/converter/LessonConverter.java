package com.company.schedule.management.system.controller.rest.converter;

import com.company.schedule.management.system.dto.LessonDto;
import com.company.schedule.management.system.model.Lesson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LessonConverter {

    private final ModelMapper modelMapper;

    public LessonConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public LessonDto convertToDto(Lesson lesson) {
        return modelMapper.map(lesson, LessonDto.class);
    }

    public Lesson convertToEntity(LessonDto lessonDto) {
        return modelMapper.map(lessonDto, Lesson.class);
    }
}
