package com.fdmgroup.backend_streamhub.livechat.service;

import com.fdmgroup.backend_streamhub.livechat.constant.KafkaConstants;
import com.fdmgroup.backend_streamhub.livechat.models.Message;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSenderService {

  SimpMessagingTemplate template;

  public MessageSenderService(SimpMessagingTemplate template) {
    this.template = template;
  }

  @KafkaListener(
      topics = KafkaConstants.KAFKA_TOPIC,
      groupId = KafkaConstants.GROUP_ID + "-sender",
      concurrency = "2")
  public void sendMessage(Message message) {
    System.out.println("Received message from Kafka by SenderService: " + message);
    template.convertAndSend("/topic/chat/" + message.getSessionId(), message);
    System.out.println(
        "Sent message from senderService: "
            + message
            + " to /topic/chat/"
            + message.getSessionId());
  }
}
