package com.company.schedule.management.system.controller.rest.converter;

import com.company.schedule.management.system.dto.TeacherDto;
import com.company.schedule.management.system.model.Teacher;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TeacherConverter {

    private final ModelMapper modelMapper;

    public TeacherConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TeacherDto convertToDto(Teacher teacher) {
        return modelMapper.map(teacher, TeacherDto.class);
    }

    public Teacher convertToEntity(TeacherDto teacherDto) {
        return modelMapper.map(teacherDto, Teacher.class);
    }
}
