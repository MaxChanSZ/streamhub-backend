package com.fdmgroup.backend_streamhub.watchpartysession.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateWatchPartyRequest {
    private String partyName;
    private Long accountID;
    private String scheduledDate;
    private String scheduledTime;
}
