package com.fdmgroup.backend_streamhub.livechat.controller;

import com.fdmgroup.backend_streamhub.livechat.constant.KafkaConstants;
import com.fdmgroup.backend_streamhub.livechat.models.VideoAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class VideoSyncController {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public VideoSyncController(KafkaTemplate<String, Object> template) {
        this.kafkaTemplate = template;
    }

    @MessageMapping("/video")
    public void handleVideoSyncAction(VideoAction action, SimpMessageHeaderAccessor headerAccessor) {
        // verify that the message received is from an authorised source
        String partyCode = (String) headerAccessor.getSessionAttributes().get("partyCode");
        String role = (String) headerAccessor.getSessionAttributes().get("role");
        if ( !action.getSessionId().equals(partyCode) ) {
            throw new AccessDeniedException("Unauthorised to send messages in this chat room");
        } else {
            System.out.println("Session ID matches");
            System.out.println(partyCode);
        }

        if ( !role.equals("host") ) {
            System.out.println("Person is not host");
            throw new AccessDeniedException("Unauthorised to control video synchronisation");
        } else {
            System.out.println("Host is allowed to send video sync messages");
        }

        // when the video sync message is received, send it to the kafka topic
        // the listener will perform the relevant action of distributing the message
        // to all clients who have subscribed to the topic

        try {
            kafkaTemplate.send(KafkaConstants.KAFKA_VIDEO_TOPIC, action).get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Exception occured while sending video sync message to Kafka: " + e);
        }
    }
}
