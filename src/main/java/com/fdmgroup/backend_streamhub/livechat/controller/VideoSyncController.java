package com.fdmgroup.backend_streamhub.livechat.controller;

import com.fdmgroup.backend_streamhub.livechat.constant.KafkaConstants;
import com.fdmgroup.backend_streamhub.livechat.models.VideoAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
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
    public void handleVideoSyncAction(VideoAction action) {
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
