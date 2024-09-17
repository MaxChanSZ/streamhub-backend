package com.fdmgroup.backend_streamhub.watchpartysession.dto;

public class CreateWatchPartyRequest {
    private String partyName;
    private Long accountID;

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
}
