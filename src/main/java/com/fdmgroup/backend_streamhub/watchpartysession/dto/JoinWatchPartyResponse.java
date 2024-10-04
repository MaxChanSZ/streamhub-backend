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
}
