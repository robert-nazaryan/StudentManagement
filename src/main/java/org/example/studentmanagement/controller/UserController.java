package org.example.studentmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.example.studentmanagement.emun.UserType;
import org.example.studentmanagement.entity.User;
import org.example.studentmanagement.security.SpringUser;
import org.example.studentmanagement.service.LessonService;
import org.example.studentmanagement.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final LessonService lessonService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/teachers")
    public String teachersPage(ModelMap modelMap) {
        modelMap.put("teachers", userService.findAllByType(UserType.TEACHER));
        return "teachers";
    }

    @GetMapping("/students")
    public String studentsPage(ModelMap modelMap, @ModelAttribute("currentUser") User currentUser) {
        modelMap.addAttribute("currentUser", currentUser);
        if (currentUser.getType() == UserType.TEACHER) {
            modelMap.addAttribute("students", userService.findAllByType(UserType.STUDENT));
        } else {
            modelMap.addAttribute("students", userService.findAllByLesson(currentUser.getLesson()));
        }
        return "students";
    }

    // Authorization

    @GetMapping("/register")
    public String chooseUserTypePage() {
        return "chooseUserType";
    }

    @GetMapping("/student/register")
    public String studentRegisterPage(@RequestParam(value = "msg", required = false) String msg, ModelMap modelMap) {
        if (msg != null && !msg.isEmpty()) {
            modelMap.addAttribute("msg", msg);
        }
        modelMap.addAttribute("lessons", lessonService.findAll());
        return "studentRegister";
    }

    @GetMapping("/teacher/register")
    public String teacherRegisterPage(@RequestParam(value = "msg", required = false) String msg, ModelMap modelMap) {
        if (msg != null && !msg.isEmpty()) {
            modelMap.addAttribute("msg", msg);
        }
        return "teacherRegister";
    }

    @PostMapping("/student/register")
    public String register(@RequestParam("picture") MultipartFile multipartFile,
                           @ModelAttribute User user) throws IOException {
        Optional<User> byEmail = userService.findByEmail(user.getEmail());
        if (byEmail.isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setType(UserType.STUDENT);
            userService.save(user, multipartFile);
            return "redirect:/student/register?msg=You are registered";
        }
        return "redirect:/student/register?msg=Email is already is use";
    }

    @PostMapping("/teacher/register")
    public String teacherRegister(@RequestParam("picture") MultipartFile multipartFile,
                                  @ModelAttribute User user) throws IOException {
        Optional<User> byEmail = userService.findByEmail(user.getEmail());
        if (byEmail.isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setType(UserType.TEACHER);
            userService.save(user, multipartFile);
            return "redirect:/teacher/register?msg=You are registered";
        }
        return "redirect:/teacher/register?msg=Email is already is use";
    }

    @GetMapping("/login")
    public String loginPage(@AuthenticationPrincipal SpringUser springUser) {
        if (springUser == null) {
            return "login";
        }
        return "redirect:/";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccessPage(@ModelAttribute("currentUser") User currentUser, ModelMap modelMap) {
        modelMap.addAttribute("currentUser", currentUser);
        return "home";
    }

    //Update

    @GetMapping("/teachers/update/{id}")
    public String updateTeacherPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<User> byId = userService.findById(id);
        if (byId.isPresent()) {
            modelMap.put("teacher", byId.get());
        } else {
            return "redirect:/teachers";
        }
        return "updateTeacher";
    }

    @GetMapping("/students/update/{id}")
    public String updateStudentPage(@PathVariable("id") int id, ModelMap modelMap) {
        Optional<User> byId = userService.findById(id);
        if (byId.isPresent()) {
            modelMap.put("student", byId.get());
            modelMap.put("lessons", lessonService.findAll());
        } else {
            return "redirect:/students";
        }
        return "updateStudent";
    }

    @PostMapping("/teachers/update")
    public String updateTeacher(@ModelAttribute User user,
                                @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        user.setType(UserType.TEACHER);
        userService.update(user, multipartFile);
        return "redirect:/teachers";
    }

    @PostMapping("/students/update")
    public String updateStudent(@ModelAttribute User user,
                                @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        user.setType(UserType.STUDENT);
        userService.update(user, multipartFile);
        return "redirect:/students";
    }

    // Delete

    @GetMapping("/teachers/delete/{id}")
    public String deleteTeacher(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/teachers";
    }

    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/students";
    }

    @GetMapping("/teachers/picture/delete")
    public String deleteTeacherPicture(@RequestParam("id") int id) {
        Optional<User> byId = userService.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            String picName = user.getPicName();
            if (picName != null) {
                user.setPicName(null);
                userService.save(user);
            }
            return "redirect:/teachers/update/" + user.getId();
        } else {
            return "redirect:/teachers";
        }
    }

    @GetMapping("/students/picture/delete")
    public String deleteUserPicture(@RequestParam("id") int id) {
        Optional<User> byId = userService.findById(id);
        if (byId.isPresent()) {

            return "redirect:/students/update/" + id;
        } else {
            return "redirect:/students";
        }
    }
}
