package org.example.studentmanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.studentmanagement.entity.Message;
import org.example.studentmanagement.repository.MessageRepository;
import org.example.studentmanagement.repository.UserRepository;
import org.example.studentmanagement.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final UserRepository userRepository;

    @Override
    public void save(Message message, int toUserId) {
        message.setToUser(userRepository.findById(toUserId).get());
        messageRepository.save(message);
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public List<Message> findAllByToUserId(int id) {
        return messageRepository.findAllByToUserId(id);
    }

}
