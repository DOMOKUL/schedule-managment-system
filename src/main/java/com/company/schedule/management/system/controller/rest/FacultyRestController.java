package com.company.schedule.management.system.controller.rest;

import com.company.schedule.management.system.controller.rest.converter.EntityConverter;
import com.company.schedule.management.system.dto.FacultyDTO;
import com.company.schedule.management.system.model.Faculty;
import com.company.schedule.management.system.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/faculties")
@RequiredArgsConstructor
public class FacultyRestController {

    private final FacultyService facultyService;
    private final EntityConverter entityConverter;

    @PostMapping()
    public ResponseEntity<FacultyDTO> addFaculty(@Valid @RequestBody FacultyDTO facultyDto) {
        Faculty faculty = entityConverter.convertDtoToEntity(facultyDto, Faculty.class);
        return new ResponseEntity<>(entityConverter.convertEntityToDto(facultyService.saveFaculty(faculty), FacultyDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyDTO> getFacultyById(@PathVariable Long id) {
        return ResponseEntity.ok(entityConverter.convertEntityToDto(facultyService.getFacultyById(id), FacultyDTO.class));
    }

    @GetMapping()
    public ResponseEntity<List<FacultyDTO>> getListOfFaculties() {
        List<Faculty> faculties = facultyService.getAllFaculties();
        return ResponseEntity.ok(faculties.stream()
                .map(faculty -> entityConverter.convertEntityToDto(faculty, FacultyDTO.class))
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable("id") Long id, @Valid @RequestBody FacultyDTO facultyDto) {
        if (!id.equals(facultyDto.getId())) {
            throw new IllegalArgumentException("IDs don't match");
        }
        facultyService.saveFaculty(entityConverter.convertDtoToEntity(facultyDto, Faculty.class));
        return ResponseEntity.ok(entityConverter.convertDtoToEntity(facultyDto, Faculty.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFacultyById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
