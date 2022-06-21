package com.company.schedule.management.system.controller.rest.converter;


import com.company.schedule.management.system.dto.FacultyDto;
import com.company.schedule.management.system.model.Faculty;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FacultyConverter {

    private final ModelMapper modelMapper;

    public FacultyConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public FacultyDto convertToDto(Faculty faculty) {
        return modelMapper.map(faculty, FacultyDto.class);
    }

    public Faculty convertToEntity(FacultyDto facultyDto) {
        return modelMapper.map(facultyDto, Faculty.class);
    }
}
