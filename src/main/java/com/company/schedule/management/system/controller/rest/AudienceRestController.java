package com.company.schedule.management.system.controller.rest;

import com.company.schedule.management.system.controller.rest.converter.EntityConverter;
import com.company.schedule.management.system.dto.AudienceDTO;
import com.company.schedule.management.system.model.Audience;
import com.company.schedule.management.system.service.AudienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/audiences")
@RequiredArgsConstructor
public class AudienceRestController {

    private final AudienceService audienceService;
    private final EntityConverter entityConverter;

    @PostMapping()
    public ResponseEntity<AudienceDTO> addAudience(@Valid @RequestBody AudienceDTO audienceDto) {
        Audience audience = entityConverter.convertDtoToEntity(audienceDto, Audience.class);
        return new ResponseEntity<>(entityConverter.convertEntityToDto(audienceService.saveAudience(audience), AudienceDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AudienceDTO> getAudienceById(@PathVariable Long id) {
        return ResponseEntity.ok(entityConverter.convertEntityToDto(audienceService.getAudienceById(id), AudienceDTO.class));
    }

    @GetMapping()
    public ResponseEntity<List<AudienceDTO>> getListOfAudiences() {
        List<Audience> audiences = audienceService.getAllAudiences();
        return ResponseEntity.ok(audiences.stream()
                .map(audience -> entityConverter.convertEntityToDto(audience, AudienceDTO.class))
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Audience> updateAudience(@PathVariable("id") Long id, @Valid @RequestBody AudienceDTO audienceDto) {
        if (!id.equals(audienceDto.getId())) {
            throw new IllegalArgumentException("IDs don't match");
        }
        audienceService.saveAudience(entityConverter.convertDtoToEntity(audienceDto, Audience.class));
        return ResponseEntity.ok(entityConverter.convertDtoToEntity(audienceDto, Audience.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAudience(@PathVariable Long id) {
        audienceService.deleteAudienceById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
