package com.company.schedule.management.system.service;

import com.company.schedule.management.system.model.Lecture;

import java.util.List;

public interface LectureService {

    Lecture saveLecture(Lecture lecture);

    Lecture getLectureById(Long id);

    List<Lecture> getAllLectures();

    Lecture updateLecture(Lecture lecture);

    void deleteLectureById(Long id);
}
