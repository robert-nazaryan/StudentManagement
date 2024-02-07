package org.example.studentmanagement.service;

import org.example.studentmanagement.emun.UserType;
import org.example.studentmanagement.entity.Lesson;
import org.example.studentmanagement.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAllByType(UserType userType);

    Optional<User> findByEmail(String email);

    void save(User user, MultipartFile multipartFile) throws IOException;

    void save(User user);

    void update(User user, MultipartFile multipartFile) throws IOException;

    Optional<User> findById(int id);

    List<User> findAllByLesson(Lesson lesson);

    void deleteById(int id);

    void deleteUserPicture(int id);
}
