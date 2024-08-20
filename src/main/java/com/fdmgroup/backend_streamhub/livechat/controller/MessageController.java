package com.fdmgroup.backend_streamhub.livechat.controller;

import com.fdmgroup.backend_streamhub.livechat.models.Message;
import java.util.ArrayList;
import java.util.List;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

  private final List<Message> messages = new ArrayList<>();

  @MessageMapping("/hello")
  @SendTo("/topic/hello")
  public List<Message> handleChatMessage(Message message) {
    System.out.println("Received message: " + message); // Print the received message content
    if (message.getSender().equals(null)) {
      message.setSender("anonymous");
    }
    messages.add(message); // Save message to list
    return messages; // Return the updated list of messages
  }

    // Optionally, add an endpoint to retrieve all messages
    @GetMapping("/api/messages")
    public List<Message> getMessages() {
        return messages;
    }
}
