package com.company.schedule.management.system.controller.rest;

import com.company.schedule.management.system.controller.rest.converter.EntityConverter;
import com.company.schedule.management.system.dto.LessonDTO;
import com.company.schedule.management.system.model.Lesson;
import com.company.schedule.management.system.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/lessons")
@RequiredArgsConstructor
public class LessonRestController {

    private final LessonService lessonService;
    private final EntityConverter entityConverter;

    @PostMapping()
    public ResponseEntity<LessonDTO> addLesson(@Valid @RequestBody LessonDTO lessonDto) {
        Lesson lesson = entityConverter.convertDtoToEntity(lessonDto, Lesson.class);
        return new ResponseEntity<>(entityConverter.convertEntityToDto(lessonService.saveLesson(lesson), LessonDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable Long id) {
        return ResponseEntity.ok(entityConverter.convertEntityToDto(lessonService.getLessonById(id), LessonDTO.class));
    }

    @GetMapping()
    public ResponseEntity<List<LessonDTO>> getListOfLessons() {
        List<Lesson> lessons = lessonService.getAllLessons();
        return ResponseEntity.ok(lessons.stream()
                .map(lesson -> entityConverter.convertEntityToDto(lesson, LessonDTO.class))
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lesson> updateLesson(@PathVariable("id") Long id, @Valid @RequestBody LessonDTO lessonDto) {
        if (!id.equals(lessonDto.getId())) {
            throw new IllegalArgumentException("IDs don't match");
        }
        lessonService.saveLesson(entityConverter.convertDtoToEntity(lessonDto, Lesson.class));
        return ResponseEntity.ok(entityConverter.convertDtoToEntity(lessonDto, Lesson.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLessonById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
