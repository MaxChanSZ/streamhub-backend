package com.fdmgroup.backend_streamhub.watchpartysession.dto;

public class UpdatePollRequest {
    private Long accountID;
    private Long pollID;
    private String question;
    private PollOptionRequest[] pollOptionRequests;

    public void setAccountID(Long accountID) {
        this.accountID = accountID;
    }

    public Long getPollID() {
        return pollID;
    }

    public Long getAccountID() {
        return accountID;
    }

    public void setPollID(Long pollID) {
        this.pollID = pollID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public PollOptionRequest[] getPollOptionRequests() {
        return pollOptionRequests;
    }

    public void setPollOptionRequests(PollOptionRequest[] pollOptionRequests) {
        this.pollOptionRequests = pollOptionRequests;
    }

}
