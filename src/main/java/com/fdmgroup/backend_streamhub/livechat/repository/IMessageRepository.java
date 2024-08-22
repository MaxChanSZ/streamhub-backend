package com.fdmgroup.backend_streamhub.livechat.repository;

import com.fdmgroup.backend_streamhub.livechat.models.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IMessageRepository extends MongoRepository<Message,Long> {

}
