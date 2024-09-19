package com.fdmgroup.backend_streamhub.livechat.models;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmojiReaction {

  private final EmojiType TYPE;

  private final String SESSION_ID;

  private final String SENDER;

  private final LocalDateTime TIMESTAMP;
}
