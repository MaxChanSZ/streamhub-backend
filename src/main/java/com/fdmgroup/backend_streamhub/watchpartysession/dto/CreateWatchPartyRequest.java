package com.fdmgroup.backend_streamhub.watchpartysession.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateWatchPartyRequest {
    private String partyName;
    private Long accountID;
<<<<<<< HEAD
    private String password;

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public Long getAccountID() {
        return accountID;
    }

    public void setAccountID(Long accountID) {
        this.accountID = accountID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
=======
    private String scheduledDate;
    private String scheduledTime;
>>>>>>> origin/SCRUM-172
}
