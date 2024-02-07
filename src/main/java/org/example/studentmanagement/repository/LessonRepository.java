package org.example.studentmanagement.repository;


import org.example.studentmanagement.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    List<Lesson> findAllByTitle(String title);
}
