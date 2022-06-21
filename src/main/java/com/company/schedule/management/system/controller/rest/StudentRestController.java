package com.company.schedule.management.system.controller.rest;

import com.company.schedule.management.system.controller.rest.converter.StudentConverter;
import com.company.schedule.management.system.dto.StudentDto;
import com.company.schedule.management.system.model.Student;
import com.company.schedule.management.system.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/students")
public class StudentRestController {

    private final StudentService studentService;
    private final StudentConverter studentConverter;

    public StudentRestController(StudentService studentService, StudentConverter studentConverter) {
        this.studentService = studentService;
        this.studentConverter = studentConverter;
    }

    @PostMapping(consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDto addStudent(@Valid @RequestBody StudentDto studentDto) {
        Student student = studentConverter.convertToEntity(studentDto);
        Student studentCreated = studentService.saveStudent(student);
        return studentConverter.convertToDto(studentCreated);
    }

    @GetMapping("/{id}")
    public StudentDto getStudentById(@PathVariable Long id) throws HttpClientErrorException {
        return studentConverter.convertToDto(studentService.getStudentById(id));
    }

    @GetMapping()
    public List<StudentDto> getListOfStudents() {
        List<Student> students = studentService.getAllStudents();
        return students.stream()
                .map(studentConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Student> updateStudent(@PathVariable("id") Long id, @Valid @RequestBody StudentDto studentDto) {
        if (!id.equals(studentDto.getId())) {
            throw new IllegalArgumentException("IDs don't match");
        }
        if (studentService.getStudentById(id) == null) {
            throw new EntityNotFoundException("Student with id: " + id + " is not found");
        }

        studentService.saveStudent(studentConverter.convertToEntity(studentDto));
        return ResponseEntity.ok(studentConverter.convertToEntity(studentDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.ok().build();
    }
}
