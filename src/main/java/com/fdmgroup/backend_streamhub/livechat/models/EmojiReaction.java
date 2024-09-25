package com.fdmgroup.backend_streamhub.livechat.models;

import java.time.LocalDateTime;

public record EmojiReaction(
    String ID, String TYPE, String SESSION_ID, String SENDER, LocalDateTime TIMESTAMP) {}
