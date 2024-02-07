package org.example.studentmanagement.service;

import org.example.studentmanagement.entity.Message;

import java.util.List;

public interface MessageService {

    void save(Message message);

    List<Message> findAll();

    List<Message> findAllByToId(int id);

}
