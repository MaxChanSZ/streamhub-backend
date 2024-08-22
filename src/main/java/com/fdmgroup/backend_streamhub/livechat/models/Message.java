package com.fdmgroup.backend_streamhub.livechat.models;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Message {

  private long messageID;
  private MessageType type;
  private String content;
  private String sender;
  private String sessionId;

  @Override
  public String toString() {
    return "Message{"
        + "type="
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
        + '}';
  }
}
