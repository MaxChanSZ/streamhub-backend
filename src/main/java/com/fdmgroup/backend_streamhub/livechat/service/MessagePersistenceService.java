package com.fdmgroup.backend_streamhub.livechat.service;

import com.fdmgroup.backend_streamhub.livechat.constant.KafkaConstants;
import com.fdmgroup.backend_streamhub.livechat.models.Message;
import com.fdmgroup.backend_streamhub.livechat.repository.IMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessagePersistenceService {
    @Autowired
    private IMessageRepository messageRepository;

    @KafkaListener(
            topics = KafkaConstants.KAFKA_TOPIC,
            groupId = "chat-persistence"
    )
    public void persistMessage(Message message)
    {
        System.out.println("Persistence Class Received message : " + message);
        // save message to database
        messageRepository.save(message);
    }

    public List<Message> findMessagesBySession(String sessionId) {
        return messageRepository.findBySessionId(sessionId);
    }

    public long deleteMessagesBySession(String sessionId) {
        // return count of messages deleted
        return messageRepository.deleteMessagesBySessionId(sessionId);
    }
}
