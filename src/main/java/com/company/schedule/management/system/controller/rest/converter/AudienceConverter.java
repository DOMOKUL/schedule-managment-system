package com.company.schedule.management.system.controller.rest.converter;

import com.company.schedule.management.system.dto.AudienceDto;
import com.company.schedule.management.system.model.Audience;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AudienceConverter {

    private final ModelMapper modelMapper;

    public AudienceConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AudienceDto convertToDto(Audience audience) {
        return modelMapper.map(audience, AudienceDto.class);
    }

    public Audience convertToEntity(AudienceDto audienceDto) {
        return modelMapper.map(audienceDto, Audience.class);
    }
}
