package com.company.schedule.management.system.controller.rest;

import com.company.schedule.management.system.controller.rest.converter.AudienceConverter;
import com.company.schedule.management.system.dto.AudienceDto;
import com.company.schedule.management.system.model.Audience;
import com.company.schedule.management.system.service.AudienceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/audiences")
public class AudienceRestController {

    private final AudienceService audienceService;
    private final AudienceConverter audienceConverter;

    public AudienceRestController(AudienceService audienceService, AudienceConverter audienceConverter) {
        this.audienceService = audienceService;
        this.audienceConverter = audienceConverter;
    }

    @PostMapping(consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public AudienceDto addAudience(@Valid @RequestBody AudienceDto audienceDto) {
        Audience audience = audienceConverter.convertToEntity(audienceDto);
        Audience audienceCreated = audienceService.saveAudience(audience);
        return audienceConverter.convertToDto(audienceCreated);
    }

    @GetMapping("/{id}")
    public AudienceDto getAudienceById(@PathVariable Long id) throws HttpClientErrorException {
        return audienceConverter.convertToDto(audienceService.getAudienceById(id));
    }

    @GetMapping()
    public List<AudienceDto> getListOfAudiences() {
        List<Audience> audiences = audienceService.getAllAudiences();
        return audiences.stream()
                .map(audienceConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Audience> updateAudience(@PathVariable("id") Long id, @Valid @RequestBody AudienceDto audienceDto) {
        if (!id.equals(audienceDto.getId())) {
            throw new IllegalArgumentException("IDs don't match");
        }
        if (audienceService.getAudienceById(id) == null) {
            throw new EntityNotFoundException("Audience with id: " + id + " is not found");
        }

        audienceService.saveAudience(audienceConverter.convertToEntity(audienceDto));
        return ResponseEntity.ok(audienceConverter.convertToEntity(audienceDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAudience(@PathVariable Long id) {
        audienceService.deleteAudienceById(id);
        return ResponseEntity.ok().build();
    }
}
