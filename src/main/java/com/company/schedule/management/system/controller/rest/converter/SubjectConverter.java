package com.company.schedule.management.system.controller.rest.converter;

import com.company.schedule.management.system.dto.SubjectDto;
import com.company.schedule.management.system.model.Subject;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SubjectConverter {

    private final ModelMapper modelMapper;

    public SubjectConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SubjectDto convertToDto(Subject subject) {
        return modelMapper.map(subject, SubjectDto.class);
    }

    public Subject convertToEntity(SubjectDto subjectDto) {
        return modelMapper.map(subjectDto, Subject.class);
    }
}
