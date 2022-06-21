package com.company.schedule.management.system.controller.rest;

import com.company.schedule.management.system.controller.rest.converter.TeacherConverter;
import com.company.schedule.management.system.dto.TeacherDto;
import com.company.schedule.management.system.model.Teacher;
import com.company.schedule.management.system.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/teachers")
public class TeacherRestController {

    private final TeacherService teacherService;
    private final TeacherConverter teacherConverter;

    public TeacherRestController(TeacherService teacherService, TeacherConverter teacherConverter) {
        this.teacherService = teacherService;
        this.teacherConverter = teacherConverter;
    }

    @PostMapping(consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherDto addTeacher(@Valid @RequestBody TeacherDto teacherDto) {
        Teacher teacher = teacherConverter.convertToEntity(teacherDto);
        Teacher teacherCreated = teacherService.saveTeacher(teacher);
        return teacherConverter.convertToDto(teacherCreated);
    }

    @GetMapping("/{id}")
    public TeacherDto getTeacherById(@PathVariable Long id) throws HttpClientErrorException {
        return teacherConverter.convertToDto(teacherService.getTeacherById(id));
    }

    @GetMapping()
    public List<TeacherDto> getListOfTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        return teachers.stream()
                .map(teacherConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Teacher> updateTeacher(@PathVariable("id") Long id, @Valid @RequestBody TeacherDto teacherDto) {
        if (!id.equals(teacherDto.getId())) {
            throw new IllegalArgumentException("IDs don't match");
        }
        if (teacherService.getTeacherById(id) == null) {
            throw new EntityNotFoundException("Teacher with id: " + id + " is not found");
        }

        Teacher teacher = teacherConverter.convertToEntity(teacherDto);
        teacherService.saveTeacher(teacher);
        return ResponseEntity.ok(teacher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacherById(id);
        return ResponseEntity.ok().build();
    }
}
