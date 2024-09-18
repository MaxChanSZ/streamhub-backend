package com.fdmgroup.backend_streamhub.livechat.service;

import com.fdmgroup.backend_streamhub.livechat.constant.KafkaConstants;
import com.fdmgroup.backend_streamhub.livechat.models.Message;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

  SimpMessagingTemplate template;

  public MessageListener(SimpMessagingTemplate template) {
    this.template = template;
  }

  @KafkaListener(
      topics = KafkaConstants.KAFKA_TOPIC,
      groupId = KafkaConstants.GROUP_ID + "-listener",
      concurrency = "2")
  public void listen(Message message) {

    System.out.println("Received message by Listener: " + message);
    //    template.convertAndSend("/topic/chat/" + message.getSessionId(), message);
    //    System.out.println("Sent message from Listener: " + message);
  }
}
