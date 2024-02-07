package org.example.studentmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.example.studentmanagement.emun.UserType;
import org.example.studentmanagement.entity.Message;
import org.example.studentmanagement.entity.User;
import org.example.studentmanagement.service.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/messages")
    public String messagesPage(@ModelAttribute("currentUser") User currentUser, ModelMap modelMap) {
        if (currentUser.getType() == UserType.TEACHER) {
            modelMap.addAttribute("messages", messageService.findAll());
        } else {
            modelMap.addAttribute("messages", messageService.findAllByToId(currentUser.getId()));
        }
        return "messages";
    }

    @GetMapping("/message/send/{id}")
    public String sendMessagePage(@PathVariable("id") int id, ModelMap modelMap) {
        modelMap.addAttribute("toId", id);
        return "sendMessage";
    }

    @PostMapping("/message/send")
    public String sendMessage(@ModelAttribute("currentUser") User currentUser,
                              @RequestParam("messageText") String messageText,
                              @RequestParam("toId") int toId) {
        Message message = new Message();
        message.setMessage(messageText);
        message.setToId(toId);
        message.setFromId(currentUser.getId());
        message.setDateTime(new Date(new java.util.Date().getTime()));
        messageService.save(message);
        return "redirect:/students";
    }
}
