package org.example.studentmanagement.service;

import org.example.studentmanagement.entity.Message;

import java.util.List;

public interface MessageService {

    void save(Message message, int toUserId);

    List<Message> findAll();

    List<Message> findAllByToUserId(int id);

}
