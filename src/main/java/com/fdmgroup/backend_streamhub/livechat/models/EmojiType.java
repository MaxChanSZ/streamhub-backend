package com.fdmgroup.backend_streamhub.livechat.models;

public enum EmojiType {
  SMILEY_FACE("🙂"),
  HEART("🩷"),
  SAD_FACE("😢");

  private final String emoji;

  EmojiType(String emoji) {
    this.emoji = emoji;
  }

  public String getEmoji() {
    return emoji;
  }
}
