package com.fdmgroup.backend_streamhub.watchpartysession.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateWatchPartyRequest {
    private String partyName;
    private Long accountId;
    private String scheduledDate;
    private String scheduledTime;
    private Long watchPartyId;
}
