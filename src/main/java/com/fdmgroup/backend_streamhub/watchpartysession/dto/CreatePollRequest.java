package com.fdmgroup.backend_streamhub.watchpartysession.dto;

public class CreatePollRequest {
    private String question;
    private String partyCode;
    private CreatePollOptionRequest[] pollOptionRequests;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }

    public CreatePollOptionRequest[] getPollOptionRequests() {
        return pollOptionRequests;
    }

    public void setPollOptionRequests(CreatePollOptionRequest[] pollOptionRequests) {
        this.pollOptionRequests = pollOptionRequests;
    }
}
