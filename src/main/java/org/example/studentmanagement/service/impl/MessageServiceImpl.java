package org.example.studentmanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.studentmanagement.entity.Message;
import org.example.studentmanagement.repository.MessageRepository;
import org.example.studentmanagement.service.MessageService;
import org.example.studentmanagement.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final UserService userService;

    @Override
    public void save(Message message) {
        messageRepository.save(message);
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public List<Message> findAllByToId(int id) {
        return messageRepository.findAllByToId(id);
    }

}
