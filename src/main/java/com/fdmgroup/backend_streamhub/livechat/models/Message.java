package com.fdmgroup.backend_streamhub.livechat.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Message {

    private MessageType type;
    private String content;
    private String sender;
    private String sessionId;

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", sessionId='" + sessionId + '\'' +
                '}';
    }
}

