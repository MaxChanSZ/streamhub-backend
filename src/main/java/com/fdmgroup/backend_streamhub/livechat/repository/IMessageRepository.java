package com.fdmgroup.backend_streamhub.livechat.repository;

import com.fdmgroup.backend_streamhub.livechat.models.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IMessageRepository extends MongoRepository<Message,Long> {
    List<Message> findBySessionId(String sessionId);
    Long deleteMessagesBySessionId(String sessionId);
}
