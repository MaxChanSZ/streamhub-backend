package com.fdmgroup.backend_streamhub.livechat.models;

import java.time.LocalDateTime;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Document("chat-messages")
public class Message {

  private long messageID;
  private MessageType type;
  private String content;
  private String sender;
  private String sessionId;
  private LocalDateTime timeStamp;

  @Override
  public String toString() {
    return "Message{"
        + "messageID="
        + messageID
        + ", type="
        + type
        + ", content='"
        + content
        + '\''
        + ", sender='"
        + sender
        + '\''
        + ", sessionId='"
        + sessionId
        + '\''
        + ", timeStamp="
        + timeStamp
        + '}';
  }
}
