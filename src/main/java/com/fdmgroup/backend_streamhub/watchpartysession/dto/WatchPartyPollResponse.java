package com.fdmgroup.backend_streamhub.watchpartysession.dto;

import com.fdmgroup.backend_streamhub.watchpartysession.model.Poll;

import java.util.List;

public class WatchPartyPollResponse {
    private long pollId;
    private String pollQuestion;
    private List<PollOptionResponse> pollOptionList;
    private boolean voted;
    private PollOptionResponse selectedPollOption;


    public PollOptionResponse getSelectedPollOption() {
        return selectedPollOption;
    }

    public void setSelectedPollOption(PollOptionResponse selectedPollOption) {
        this.selectedPollOption = selectedPollOption;
    }

    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    public long getPollId() {
        return pollId;
    }

    public void setPollId(long pollId) {
        this.pollId = pollId;
    }

    public String getPollQuestion() {
        return pollQuestion;
    }

    public void setPollQuestion(String pollQuestion) {
        this.pollQuestion = pollQuestion;
    }

    public List<PollOptionResponse> getPollOptionList() {
        return pollOptionList;
    }

    public void setPollOptionList(List<PollOptionResponse> pollOptionList) {
        this.pollOptionList = pollOptionList;
    }
}
