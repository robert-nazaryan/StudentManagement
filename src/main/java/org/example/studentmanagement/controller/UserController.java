package org.example.studentmanagement.controller;

import org.example.studentmanagement.emun.UserType;
import org.example.studentmanagement.entity.User;
import org.example.studentmanagement.repository.LessonRepository;
import org.example.studentmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Value("${picture.upload.directory}")
    private String uploadDirectory;

    @GetMapping("/teachers")
    public String teachersPage(ModelMap modelMap) {
        modelMap.put("teachers", userRepository.findAllByType(UserType.TEACHER));
        return "teachers";
    }

    @GetMapping("/students")
    public String studentsPage(ModelMap modelMap) {
        modelMap.put("students", userRepository.findAllByType(UserType.STUDENT));
        return "students";
    }

    // Add

    @GetMapping("/teachers/add")
    public String addTeacherPage() {
        return "addTeacher";
    }

    @GetMapping("/students/add")
    public String addStudentPage(ModelMap modelMap) {
        modelMap.put("lessons", lessonRepository.findAll());
        return "addStudent";
    }

    @PostMapping("/teachers/add")
    public String addTeacher(@ModelAttribute User user,
                             @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        user.setType(UserType.TEACHER);
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(uploadDirectory, picName);
            multipartFile.transferTo(file);
            user.setPicName(picName);
        }
        userRepository.save(user);
        return "redirect:/teachers";
    }

    @PostMapping("/students/add")
    public String addStudent(@ModelAttribute User user,
                             @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        user.setType(UserType.STUDENT);
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(uploadDirectory, picName);
            multipartFile.transferTo(file);
            user.setPicName(picName);
        }
        userRepository.save(user);
        return "redirect:/students";
    }

    //Update

    @GetMapping("/teachers/update/{id}")
    public String updateTeacherPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            modelMap.put("teacher", byId.get());
        } else {
            return "redirect:/teachers";
        }
        return "updateTeacher";
    }

    @GetMapping("/students/update/{id}")
    public String updateStudentPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            modelMap.put("student", byId.get());
            modelMap.put("lessons", lessonRepository.findAll());
        } else {
            return "redirect:/students";
        }
        return "updateStudent";
    }

    @PostMapping("/teachers/update")
    public String updateTeacher(@ModelAttribute User user,
                                @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        user.setType(UserType.TEACHER);
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(uploadDirectory, picName);
            multipartFile.transferTo(file);
            user.setPicName(picName);
        } else {
            Optional<User> byId = userRepository.findById(user.getId());
            user.setPicName(byId.get().getPicName());
        }
        userRepository.save(user);
        return "redirect:/teachers";
    }

    @PostMapping("/students/update")
    public String updateStudent(@ModelAttribute User user,
                                @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        user.setType(UserType.STUDENT);
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(uploadDirectory, picName);
            multipartFile.transferTo(file);
            user.setPicName(picName);
        } else {
            Optional<User> byId = userRepository.findById(user.getId());
            user.setPicName(byId.get().getPicName());
        }
        userRepository.save(user);
        return "redirect:/students";
    }

    // Delete

    @GetMapping("/teachers/delete/{id}")
    public String deleteTeacher(@PathVariable("id") int id) {
        userRepository.deleteById(id);
        return "redirect:/teachers";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") int id) {
        userRepository.deleteById(id);
        return "redirect:/students";
    }

    @GetMapping("/teachers/picture/delete")
    public String deleteTeacherPicture(@RequestParam("id") int id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            String picName = user.getPicName();
            if (picName != null) {
                user.setPicName(null);
                userRepository.save(user);
            }
            return "redirect:/teachers/update/" + user.getId();
        } else {
            return "redirect:/teachers";
        }
    }

    @GetMapping("/students/picture/delete")
    public String deleteStudentPicture(@RequestParam("id") int id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            String picName = user.getPicName();
            if (picName != null) {
                user.setPicName(null);
                userRepository.save(user);
            }
            return "redirect:/students/update/" + user.getId();
        } else {
            return "redirect:/students";
        }
    }
}
