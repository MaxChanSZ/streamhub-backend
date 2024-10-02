package com.fdmgroup.backend_streamhub.watchpartysession.dto;

public class CreatePollRequest {
    private String question;
    private Long watchPartyID;
    private CreatePollOptionRequest[] pollOptionRequests;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Long getWatchPartyID() {
        return watchPartyID;
    }

    public void setWatchPartyID(Long watchPartyID) {
        this.watchPartyID = watchPartyID;
    }

    public CreatePollOptionRequest[] getPollOptionRequests() {
        return pollOptionRequests;
    }

    public void setPollOptionRequests(CreatePollOptionRequest[] pollOptionRequests) {
        this.pollOptionRequests = pollOptionRequests;
    }
}
