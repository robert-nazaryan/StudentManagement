package org.example.studentmanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.studentmanagement.emun.UserType;
import org.example.studentmanagement.entity.Lesson;
import org.example.studentmanagement.entity.User;
import org.example.studentmanagement.repository.UserRepository;
import org.example.studentmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Value("${picture.upload.directory}")
    private String uploadDirectory;

    @Override
    public List<User> findAllByType(UserType userType) {
        return userRepository.findAllByType(userType);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(User user, MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(uploadDirectory, picName);
            multipartFile.transferTo(file);
            user.setPicName(picName);
        }
        userRepository.save(user);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user, MultipartFile multipartFile) throws IOException {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String picName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            File file = new File(uploadDirectory, picName);
            multipartFile.transferTo(file);
            user.setPicName(picName);
        } else {
            Optional<User> byId = findById(user.getId());
            user.setPicName(byId.get().getPicName());
        }
        userRepository.save(user);
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAllByLesson(Lesson lesson) {
        return userRepository.findAllByLesson(lesson);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteUserPicture(int id) {
        Optional<User> byId = findById(id);
        User user = byId.get();
        String picName = user.getPicName();
        if (picName != null) {
            user.setPicName(null);
            userRepository.save(user);
        }
    }

}
