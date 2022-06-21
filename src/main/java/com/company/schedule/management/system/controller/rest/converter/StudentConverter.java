package com.company.schedule.management.system.controller.rest.converter;

import com.company.schedule.management.system.dto.StudentDto;
import com.company.schedule.management.system.model.Student;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class StudentConverter {

    private final ModelMapper modelMapper;

    public StudentConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public StudentDto convertToDto(Student student) {
        return modelMapper.map(student, StudentDto.class);
    }

    public Student convertToEntity(StudentDto studentDto) {
        return modelMapper.map(studentDto, Student.class);
    }
}
