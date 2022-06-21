package com.company.schedule.management.system.controller.rest;

import com.company.schedule.management.system.controller.rest.converter.LectureConverter;
import com.company.schedule.management.system.dto.LectureDto;
import com.company.schedule.management.system.model.Lecture;
import com.company.schedule.management.system.service.LectureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/lectures")
public class LectureRestController {

    private final LectureService lectureService;
    private final LectureConverter lectureConverter;

    public LectureRestController(LectureService lectureService, LectureConverter lectureConverter) {
        this.lectureService = lectureService;
        this.lectureConverter = lectureConverter;
    }

    @PostMapping(consumes = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    public LectureDto addLecture(@Valid @RequestBody LectureDto lectureDto) {
        Lecture lecture = lectureConverter.convertToEntity(lectureDto);
        Lecture lectureCreated = lectureService.saveLecture(lecture);
        return lectureConverter.convertToDto(lectureCreated);
    }

    @GetMapping("/{id}")
    public LectureDto getLectureById(@PathVariable Long id) throws HttpClientErrorException {
        return lectureConverter.convertToDto(lectureService.getLectureById(id));
    }

    @GetMapping()
    public List<LectureDto> getListOfLectures() {
        List<Lecture> lectures = lectureService.getAllLectures();
        return lectures.stream()
                .map(lectureConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Lecture> updateLecture(@PathVariable("id") Long id, @Valid @RequestBody LectureDto lectureDto) {
        if (!id.equals(lectureDto.getId())) {
            throw new IllegalArgumentException("IDs don't match");
        }
        if (lectureService.getLectureById(id) == null) {
            throw new EntityNotFoundException("Lecture with id: " + id + " is not found");
        }

        lectureService.saveLecture(lectureConverter.convertToEntity(lectureDto));
        return ResponseEntity.ok(lectureConverter.convertToEntity(lectureDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLecture(@PathVariable Long id) {
        lectureService.deleteLectureById(id);
        return ResponseEntity.ok().build();
    }
}
