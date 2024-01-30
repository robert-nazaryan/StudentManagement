package org.example.studentmanagement.controller;

import org.example.studentmanagement.emun.UserType;
import org.example.studentmanagement.entity.Lesson;
import org.example.studentmanagement.entity.User;
import org.example.studentmanagement.repository.LessonRepository;
import org.example.studentmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
public class LessonController {
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/lessons")
    public String lessonsPage(ModelMap modelMap) {
        modelMap.put("lessons", lessonRepository.findAll());
        return "lessons";
    }

    @GetMapping("/lessons/add")
    public String addLessonsPage(ModelMap modelMap) {
        modelMap.put("teachers", userRepository.findAllByType(UserType.TEACHER));
        return "addLesson";
    }

    @PostMapping("/lessons/add")
    public String addLesson(@ModelAttribute Lesson lesson) {
        lessonRepository.save(lesson);
        return "redirect:/lessons";
    }

    @GetMapping("/lessons/delete/{id}")
    public String deleteTeacher(@PathVariable("id") int id) {
        lessonRepository.deleteById(id);
        return "redirect:/lessons";
    }

    @GetMapping("/lessons/update/{id}")
    public String updateTeacherPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<Lesson> byId = lessonRepository.findById(id);
        if (byId.isPresent()) {
            modelMap.put("lesson", byId.get());
            modelMap.put("teachers", userRepository.findAllByType(UserType.TEACHER));
        } else {
            return "redirect:/lessons";
        }
        return "updateLesson";
    }

    @PostMapping("/lessons/update")
    public String updateTeacher(@ModelAttribute Lesson lesson) {
        lessonRepository.save(lesson);
        return "redirect:/lessons";
    }
}
