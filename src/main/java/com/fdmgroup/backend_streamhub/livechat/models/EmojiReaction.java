package com.fdmgroup.backend_streamhub.livechat.models;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class EmojiReaction {

  private final String ID;

  private final String TYPE;

  private final String SESSION_ID;

  private final String SENDER;

  private final LocalDateTime TIMESTAMP;
}
