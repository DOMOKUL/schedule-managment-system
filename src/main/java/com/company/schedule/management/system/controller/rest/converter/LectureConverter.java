package com.company.schedule.management.system.controller.rest.converter;

import com.company.schedule.management.system.dto.LectureDto;
import com.company.schedule.management.system.model.Lecture;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LectureConverter {

    private final ModelMapper modelMapper;

    public LectureConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public LectureDto convertToDto(Lecture lecture) {
        return modelMapper.map(lecture, LectureDto.class);
    }

    public Lecture convertToEntity(LectureDto lectureDto) {
        return modelMapper.map(lectureDto, Lecture.class);
    }
}
