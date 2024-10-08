package com.fdmgroup.backend_streamhub.watchpartysession.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinWatchPartyResponse {
    private String token;
    private String videoSource;
    private String roomId;
    private boolean isHost;

    public JoinWatchPartyResponse(String token, String videoSource, String roomId, boolean isHost) {
        this.token = token;
        this.videoSource = videoSource;
        this.roomId = roomId;
        this.isHost = isHost;
    }

    public JoinWatchPartyResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getVideoSource() {
        return videoSource;
    }

    public void setVideoSource(String videoSource) {
        this.videoSource = videoSource;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }
}
