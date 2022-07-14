package com.company.schedule.management.system.controller.rest;

import com.company.schedule.management.system.controller.rest.converter.EntityConverter;
import com.company.schedule.management.system.dto.SubjectDTO;
import com.company.schedule.management.system.model.Subject;
import com.company.schedule.management.system.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/subjects")
@RequiredArgsConstructor
public class SubjectRestController {

    private final SubjectService subjectService;
    private final EntityConverter entityConverter;

    @PostMapping()
    public ResponseEntity<SubjectDTO> addSubject(@Valid @RequestBody SubjectDTO subjectDto) {
        Subject subject = entityConverter.convertDtoToEntity(subjectDto, Subject.class);
        return new ResponseEntity<>(entityConverter.convertEntityToDto(subjectService.saveSubject(subject), SubjectDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> getSubjectById(@PathVariable Long id) {
        return ResponseEntity.ok(entityConverter.convertEntityToDto(subjectService.getSubjectById(id), SubjectDTO.class));
    }

    @GetMapping()
    public ResponseEntity<List<SubjectDTO>> getListOfSubjects() {
        List<Subject> subjects = subjectService.getAllSubjects();
        return ResponseEntity.ok(subjects.stream()
                .map(subject -> entityConverter.convertEntityToDto(subject, SubjectDTO.class))
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable("id") Long id, @Valid @RequestBody SubjectDTO subjectDto) {
        if (!id.equals(subjectDto.getId())) {
            throw new IllegalArgumentException("IDs don't match");
        }
        Subject subject = entityConverter.convertDtoToEntity(subjectDto, Subject.class);
        subjectService.saveSubject(subject);
        return ResponseEntity.ok(subject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubjectById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
