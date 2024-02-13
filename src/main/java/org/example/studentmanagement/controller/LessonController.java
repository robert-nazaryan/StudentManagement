package org.example.studentmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.example.studentmanagement.emun.UserType;
import org.example.studentmanagement.entity.Lesson;
import org.example.studentmanagement.entity.User;
import org.example.studentmanagement.service.LessonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(value = "/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @GetMapping("")
    public String lessonsPage(ModelMap modelMap, @ModelAttribute("currentUser") User currentUser) {
        if (currentUser.getType() == UserType.TEACHER) {
            modelMap.addAttribute("lessons", lessonService.findAll());
        } else {
            modelMap.addAttribute("lessons", currentUser.getLesson());
        }
        return "lessons";
    }

    @GetMapping("/add")
    public String addLessonsPage() {
        return "addLesson";
    }

    @PostMapping("/add")
    public String addLesson(@ModelAttribute Lesson lesson, @ModelAttribute("currentUser") User currentUser) {
        lesson.setTeacher(currentUser);
        lessonService.save(lesson);
        return "redirect:/lessons";
    }

    @GetMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable("id") int id) {
        lessonService.deleteById(id);
        return "redirect:/lessons";
    }

    @GetMapping("/update/{id}")
    public String updateTeacherPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<Lesson> byId = lessonService.findById(id);
        if (byId.isPresent()) {
            modelMap.put("lesson", byId.get());
        } else {
            return "redirect:/lessons";
        }
        return "updateLesson";
    }

    @PostMapping("/update")
    public String updateTeacher(@ModelAttribute Lesson lesson, @RequestParam("lessonId") int lessonId,
                                @ModelAttribute("currentUser") User currentUser) {
        lesson.setId(lessonId);
        lesson.setTeacher(currentUser);
        lessonService.save(lesson);
        return "redirect:/lessons";
    }
}
