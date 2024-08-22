package com.fdmgroup.backend_streamhub.livechat.service;

import com.fdmgroup.backend_streamhub.livechat.models.Message;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {
//    @Autowired
//    SimpMessagingTemplate template;

    @KafkaListener(
            topics = "streamhub-chat",
            groupId = "random"
    )
    public void listen(Message message)
    {
        System.out.println("Received message : " + message);
    }

}
