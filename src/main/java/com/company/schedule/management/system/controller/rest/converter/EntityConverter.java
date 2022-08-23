package com.company.schedule.management.system.controller.rest.converter;

import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@Data
public class EntityConverter {

    private final ModelMapper modelMapper;

    public <T, S> T convertEntityToDto(final S entity, Class<T> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public <T, S> T convertDtoToEntity(final S dto, Class<T> entityClass) {
        return modelMapper.map(dto, entityClass);
    }
}
