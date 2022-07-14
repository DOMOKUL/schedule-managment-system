package com.company.schedule.management.system.controller.rest;

import com.company.schedule.management.system.controller.rest.converter.EntityConverter;
import com.company.schedule.management.system.dto.LectureDTO;
import com.company.schedule.management.system.model.Lecture;
import com.company.schedule.management.system.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/lectures")
@RequiredArgsConstructor
public class LectureRestController {

    private final LectureService lectureService;
    private final EntityConverter entityConverter;

    @PostMapping()
    public ResponseEntity<LectureDTO> addLecture(@Valid @RequestBody LectureDTO lectureDto) {
        Lecture lecture = entityConverter.convertDtoToEntity(lectureDto, Lecture.class);
        return new ResponseEntity<>(entityConverter.convertEntityToDto(lectureService.saveLecture(lecture), LectureDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LectureDTO> getLectureById(@PathVariable Long id) {
        return ResponseEntity.ok(entityConverter.convertEntityToDto(lectureService.getLectureById(id), LectureDTO.class));
    }

    @GetMapping()
    public ResponseEntity<List<LectureDTO>> getListOfLectures() {
        List<Lecture> lectures = lectureService.getAllLectures();
        return ResponseEntity.ok(lectures.stream()
                .map(lecture -> entityConverter.convertEntityToDto(lecture, LectureDTO.class))
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lecture> updateLecture(@PathVariable("id") Long id, @Valid @RequestBody LectureDTO lectureDto) {
        if (!id.equals(lectureDto.getId())) {
            throw new IllegalArgumentException("IDs don't match");
        }
        lectureService.saveLecture(entityConverter.convertDtoToEntity(lectureDto, Lecture.class));
        return ResponseEntity.ok(entityConverter.convertDtoToEntity(lectureDto, Lecture.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLecture(@PathVariable Long id) {
        lectureService.deleteLectureById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
