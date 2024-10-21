package com.fdmgroup.backend_streamhub.watchpartysession.dto;

public class CreatePollRequest {
    private String question;
    private String partyCode;
    private PollOptionRequest[] pollOptionRequests;

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

    public PollOptionRequest[] getPollOptionRequests() {
        return pollOptionRequests;
    }

    public void setPollOptionRequests(PollOptionRequest[] pollOptionRequests) {
        this.pollOptionRequests = pollOptionRequests;
    }
}
