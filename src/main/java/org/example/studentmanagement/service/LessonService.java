package org.example.studentmanagement.service;

import org.example.studentmanagement.entity.Lesson;

import java.util.List;
import java.util.Optional;

public interface LessonService {

    void save(Lesson lesson);

    void deleteById(int id);

    Optional<Lesson> findById(int id);

    List<Lesson> findAll();

    List<Lesson> findAllByTitle(String title);

}
