package com.fdmgroup.backend_streamhub.livechat.controller;

import com.fdmgroup.backend_streamhub.livechat.models.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MessageController {


    private List<Message> messages = new ArrayList<>();
    @MessageMapping("/hello")
    @SendTo("/topic/hello")
    public List<Message> handleChatMessage(Message message) {
        messages.add(message);  // Append the message to the list
        return messages;  // Return the updated list of messages
    }

    // Optionally, add an endpoint to retrieve all messages
    @GetMapping("/api/messages")
    public List<Message> getMessages() {
        return messages;
    }
}
