package org.example.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.studentmanagement.emun.UserType;

import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String surname;

    private String email;

    private String password;

    private String picName;

    @ManyToOne
    private Lesson lesson;

    @Enumerated(EnumType.STRING)
    private UserType type;

}
