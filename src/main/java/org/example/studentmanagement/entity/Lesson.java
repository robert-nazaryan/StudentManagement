package org.example.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "lesson")
@Data
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private double duration;


    private double price;

    private Date startDate;

    @ManyToOne
    private User teacher;

}
