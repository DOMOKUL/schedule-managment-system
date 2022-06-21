package com.company.schedule.management.system.controller.rest;

import com.company.schedule.management.system.controller.rest.converter.LessonConverter;
import com.company.schedule.management.system.dto.LessonDto;
import com.company.schedule.management.system.model.Lesson;
import com.company.schedule.management.system.service.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/lessons")
public class LessonRestController {

    private final LessonService lessonService;
    private final LessonConverter lessonConverter;

    public LessonRestController(LessonService lessonService, LessonConverter lessonConverter) {
        this.lessonService = lessonService;
        this.lessonConverter = lessonConverter;
    }

    @PostMapping(consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public LessonDto addLesson(@Valid @RequestBody LessonDto lessonDto) {
        Lesson lesson = lessonConverter.convertToEntity(lessonDto);
        Lesson lessonCreated = lessonService.saveLesson(lesson);
        return lessonConverter.convertToDto(lessonCreated);
    }

    @GetMapping("/{id}")
    public LessonDto getLessonById(@PathVariable Long id) throws HttpClientErrorException {
        return lessonConverter.convertToDto(lessonService.getLessonById(id));
    }

    @GetMapping()
    public List<LessonDto> getListOfLessons() {
        List<Lesson> lessons = lessonService.getAllLessons();
        return lessons.stream()
                .map(lessonConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Lesson> updateLesson(@PathVariable("id") Long id, @Valid @RequestBody LessonDto lessonDto) {
        if (!id.equals(lessonDto.getId())) {
            throw new IllegalArgumentException("IDs don't match");
        }
        if (lessonService.getLessonById(id) == null) {
            throw new EntityNotFoundException("Lesson with id: " + id + " is not found");
        }

        lessonService.saveLesson(lessonConverter.convertToEntity(lessonDto));
        return ResponseEntity.ok(lessonConverter.convertToEntity(lessonDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLessonById(id);
        return ResponseEntity.ok().build();
    }
}
