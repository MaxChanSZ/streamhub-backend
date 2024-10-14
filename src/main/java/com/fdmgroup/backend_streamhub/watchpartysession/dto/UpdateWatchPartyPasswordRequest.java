package com.fdmgroup.backend_streamhub.watchpartysession.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateWatchPartyPasswordRequest {
    private String password;
    private Long accountId;
    private Long watchPartyId;
}
