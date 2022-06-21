package com.company.schedule.management.system.controller.rest.converter;

import com.company.schedule.management.system.dto.GroupDto;
import com.company.schedule.management.system.model.Group;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GroupConverter {

    private final ModelMapper modelMapper;

    public GroupConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GroupDto convertToDto(Group group) {
        return modelMapper.map(group, GroupDto.class);
    }

    public Group convertToEntity(GroupDto groupDto) {
        return modelMapper.map(groupDto, Group.class);
    }
}
