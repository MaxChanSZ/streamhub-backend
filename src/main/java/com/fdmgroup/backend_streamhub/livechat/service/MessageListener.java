package com.fdmgroup.backend_streamhub.livechat.service;

import com.fdmgroup.backend_streamhub.livechat.constant.KafkaConstants;
import com.fdmgroup.backend_streamhub.livechat.models.Message;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    @KafkaListener(
            topics = KafkaConstants.KAFKA_TOPIC,
            groupId = "test-listener"
    )
    public void listen(Message message)
    {
        System.out.println("Test Listener received message : " + message);
    }

}
