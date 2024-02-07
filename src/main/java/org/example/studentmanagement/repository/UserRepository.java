package org.example.studentmanagement.repository;

import org.example.studentmanagement.emun.UserType;
import org.example.studentmanagement.entity.Lesson;
import org.example.studentmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAllByType(UserType type);

    Optional<User> findByEmail(String email);

    List<User> findAllByLesson(Lesson lesson);
}
