package com.company.schedule.management.system.controller.rest;

import com.company.schedule.management.system.controller.rest.converter.EntityConverter;
import com.company.schedule.management.system.dto.StudentDTO;
import com.company.schedule.management.system.model.Student;
import com.company.schedule.management.system.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentRestController {

    private final StudentService studentService;
    private final EntityConverter entityConverter;

    @PostMapping()
    public ResponseEntity<StudentDTO> addStudent(@Valid @RequestBody StudentDTO studentDto) {
        Student student = entityConverter.convertDtoToEntity(studentDto, Student.class);
        return new ResponseEntity<>(entityConverter.convertEntityToDto(studentService.saveStudent(student), StudentDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(entityConverter.convertEntityToDto(studentService.getStudentById(id), StudentDTO.class));
    }

    @GetMapping()
    public ResponseEntity<List<StudentDTO>> getListOfStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students.stream()
                .map(student -> entityConverter.convertEntityToDto(student, StudentDTO.class))
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") Long id, @Valid @RequestBody StudentDTO studentDto) {
        if (!id.equals(studentDto.getId())) {
            throw new IllegalArgumentException("IDs don't match");
        }
        studentService.saveStudent(entityConverter.convertDtoToEntity(studentDto, Student.class));
        return ResponseEntity.ok(entityConverter.convertDtoToEntity(studentDto, Student.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
