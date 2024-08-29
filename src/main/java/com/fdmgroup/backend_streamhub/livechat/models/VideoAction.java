package com.fdmgroup.backend_streamhub.livechat.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VideoAction {
    private String actionType;
    private LocalDateTime actionTime;
    private LocalTime videoTime;
    private String sessionId;
}
