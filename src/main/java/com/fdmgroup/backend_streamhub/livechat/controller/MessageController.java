package com.fdmgroup.backend_streamhub.livechat.controller;

import com.fdmgroup.backend_streamhub.livechat.constant.KafkaConstants;
import com.fdmgroup.backend_streamhub.livechat.models.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

  @Autowired
  private KafkaTemplate<String,Object> kafkaTemplate;

  private final List<Message> messages = new ArrayList<>();

  @MessageMapping("/chat")
  @SendTo("/topic/chat")
  public Message handleChatMessage(Message message) {
    if (message.getSender().equals(null)) {
      message.setSender("anonymous");
    }
    messages.add(message); // Save message to list

    // send a message to the kafka topic
    try {
      //Sending the message to kafka topic queue
      kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
      System.out.println("Message sent to kafka");
    } catch (InterruptedException | ExecutionException e) {
      System.out.println("Error sending message to kafka");
    }

    return message; // Return the updated list of messages
  }

    // Optionally, add an endpoint to retrieve all messages
    @GetMapping("/api/messages")
    public List<Message> getMessages() {
        return messages;
    }
}
