package com.fdmgroup.backend_streamhub.livechat.controller;

import com.fdmgroup.backend_streamhub.livechat.constant.KafkaConstants;
import com.fdmgroup.backend_streamhub.livechat.models.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

  @Autowired private KafkaTemplate<String, Object> kafkaTemplate;

  private final List<Message> messages = new ArrayList<>();
  private long counter = 0;
  private final SimpMessagingTemplate template;

  public MessageController(SimpMessagingTemplate template) {
    this.template = template;
  }

  @MessageMapping("/chat")
  public void handleChatMessage(Message message) {
    System.out.println("Received message: " + message); // Print the received message content

    if (message.getSender().equals(null)) {
      message.setSender("anonymous");
    }
    message.setMessageID(counter++);
    messages.add(message); // Save message to list

    template.convertAndSend("/topic/chat/" + message.getSessionId(), message);

    // send a message to the kafka topic
    try {
      // Sending the message to kafka topic queue
      kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
      System.out.println("Message sent to kafka");
    } catch (InterruptedException | ExecutionException e) {
      System.out.println("Error sending message to kafka");
    }
  }

  /*
   * The @SendTo annotation in the provided method is used to specify the destination to which the return value of the method should be sent.
   * In this case, the method handleChatMessage is annotated with @SendTo("/topic/hello"), indicating that the updated list of messages
   * returned by this method should be sent to the specified destination /topic/hello.
   *
   * When a message is sent to the /hello endpoint, the method processes the message, adds it to the messages list,
   * and then returns the updated list of messages. The @SendTo annotation ensures that this updated list is then broadcasted
   * to all subscribers listening on the /topic/hello destination.
   *
   * For example, if a client sends a message to the /app/hello endpoint, the message will be processed by the
   * handleChatMessage method,
   * and the updated list of messages will be sent to all subscribers listening on the /topic/hello destination, allowing them
   * to receive the latest messages in real-time.
   *
   */

  // Returns all messages
  @GetMapping("/api/messages")
  public List<Message> getMessages() {
    return messages;
  }

  @GetMapping("/api/clearMessages")
  public void clearMessages() {
    System.out.println("Clearing messages");
    messages.clear();
  }
}
