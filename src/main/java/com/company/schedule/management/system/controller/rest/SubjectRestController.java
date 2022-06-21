package com.company.schedule.management.system.controller.rest;

import com.company.schedule.management.system.controller.rest.converter.SubjectConverter;
import com.company.schedule.management.system.dto.SubjectDto;
import com.company.schedule.management.system.model.Subject;
import com.company.schedule.management.system.service.SubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/subjects")
public class SubjectRestController {

    private final SubjectService subjectService;
    private final SubjectConverter subjectConverter;

    public SubjectRestController(SubjectService subjectService, SubjectConverter subjectConverter) {
        this.subjectService = subjectService;
        this.subjectConverter = subjectConverter;
    }

    @PostMapping(consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectDto addSubject(@Valid @RequestBody SubjectDto subjectDto) {
        Subject subject = subjectConverter.convertToEntity(subjectDto);
        Subject subjectCreated = subjectService.saveSubject(subject);
        return subjectConverter.convertToDto(subjectCreated);
    }

    @GetMapping("/{id}")
    public SubjectDto getSubjectById(@PathVariable Long id) throws HttpClientErrorException {
        return subjectConverter.convertToDto(subjectService.getSubjectById(id));
    }

    @GetMapping()
    public List<SubjectDto> getListOfSubjects() {
        List<Subject> subjects = subjectService.getAllSubjects();
        return subjects.stream()
                .map(subjectConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Subject> updateSubject(@PathVariable("id") Long id, @Valid @RequestBody SubjectDto subjectDto) {
        if (!id.equals(subjectDto.getId())) {
            throw new IllegalArgumentException("IDs don't match");
        }
        if (subjectService.getSubjectById(id) == null) {
            throw new EntityNotFoundException("Subject with id: " + id + " is not found");
        }

        Subject subject = subjectConverter.convertToEntity(subjectDto);
        subjectService.saveSubject(subject);
        return ResponseEntity.ok(subject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubjectById(id);
        return ResponseEntity.ok().build();
    }
}
