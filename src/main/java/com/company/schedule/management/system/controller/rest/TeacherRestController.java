package com.company.schedule.management.system.controller.rest;

import com.company.schedule.management.system.controller.rest.converter.EntityConverter;
import com.company.schedule.management.system.dto.TeacherDTO;
import com.company.schedule.management.system.model.Teacher;
import com.company.schedule.management.system.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/teachers")
@RequiredArgsConstructor
public class TeacherRestController {

    private final TeacherService teacherService;
    private final EntityConverter entityConverter;

    @PostMapping()
    public ResponseEntity<TeacherDTO> addTeacher(@Valid @RequestBody TeacherDTO teacherDto) {
        Teacher teacher = entityConverter.convertDtoToEntity(teacherDto, Teacher.class);
        return new ResponseEntity<>(entityConverter.convertEntityToDto(teacherService.saveTeacher(teacher), TeacherDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable Long id) throws HttpClientErrorException {
        return ResponseEntity.ok(entityConverter.convertEntityToDto(teacherService.getTeacherById(id), TeacherDTO.class));
    }

    @GetMapping()
    public ResponseEntity<List<TeacherDTO>> getListOfTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(teachers.stream()
                .map(teacher -> entityConverter.convertEntityToDto(teacher, TeacherDTO.class))
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable("id") Long id, @Valid @RequestBody TeacherDTO teacherDto) {
        if (!id.equals(teacherDto.getId())) {
            throw new IllegalArgumentException("IDs don't match");
        }
        Teacher teacher = entityConverter.convertDtoToEntity(teacherDto, Teacher.class);
        teacherService.saveTeacher(teacher);
        return ResponseEntity.ok(teacher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacherById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
