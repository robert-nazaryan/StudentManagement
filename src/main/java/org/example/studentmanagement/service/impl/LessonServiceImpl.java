package org.example.studentmanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.studentmanagement.entity.Lesson;
import org.example.studentmanagement.repository.LessonRepository;
import org.example.studentmanagement.service.LessonService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    @Override
    public void save(Lesson lesson) {
        lessonRepository.save(lesson);
    }

    @Override
    public void deleteById(int id) {
        lessonRepository.deleteById(id);
    }

    @Override
    public Optional<Lesson> findById(int id) {
        return lessonRepository.findById(id);
    }

    @Override
    public List<Lesson> findAll() {
        return lessonRepository.findAll();
    }

    @Override
    public List<Lesson> findAllByTitle(String title) {
        return lessonRepository.findAllByTitle(title);
    }


}
