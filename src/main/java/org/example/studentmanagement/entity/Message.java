package org.example.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Entity
@Table(name = "message")
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String message;

    private int fromId;

    private int toId;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date dateTime;

}
