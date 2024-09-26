package com.fdmgroup.backend_streamhub.watchpartysession.dto;

public class CreatePollOptionRequest {
    private String value;
    private Long pollID;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getPollID() {
        return pollID;
    }

    public void setPollID(Long pollID) {
        this.pollID = pollID;
    }
}
