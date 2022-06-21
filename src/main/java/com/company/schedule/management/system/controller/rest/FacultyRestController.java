package com.company.schedule.management.system.controller.rest;

import com.company.schedule.management.system.controller.rest.converter.FacultyConverter;
import com.company.schedule.management.system.dto.FacultyDto;
import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.service.FacultyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/faculties")
public class FacultyRestController {

    private final FacultyService facultyService;
    private final FacultyConverter facultyConverter;

    public FacultyRestController(FacultyService facultyService, FacultyConverter facultyConverter) {
        this.facultyService = facultyService;
        this.facultyConverter = facultyConverter;
    }

    @PostMapping(consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public FacultyDto addFaculty(@Valid @RequestBody FacultyDto facultyDto) {
        Faculty faculty = facultyConverter.convertToEntity(facultyDto);
        Faculty facultyCreated = facultyService.saveFaculty(faculty);
        return facultyConverter.convertToDto(facultyCreated);
    }

    @GetMapping("/{id}")
    public FacultyDto getFacultyById(@PathVariable Long id) throws HttpClientErrorException {
        return facultyConverter.convertToDto(facultyService.getFacultyById(id));
    }

    @GetMapping()
    public List<FacultyDto> getListOfFaculties() {
        List<Faculty> faculties = facultyService.getAllFaculties();
        return faculties.stream()
                .map(facultyConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Faculty> updateFaculty(@PathVariable("id") Long id, @Valid @RequestBody FacultyDto facultyDto) {
        if (!id.equals(facultyDto.getId())) {
            throw new IllegalArgumentException("IDs don't match");
        }
        if (facultyService.getFacultyById(id) == null) {
            throw new EntityNotFoundException("Faculty with id: " + id + " is not found");
        }

        facultyService.saveFaculty(facultyConverter.convertToEntity(facultyDto));
        return ResponseEntity.ok(facultyConverter.convertToEntity(facultyDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFacultyById(id);
        return ResponseEntity.ok().build();
    }
}
