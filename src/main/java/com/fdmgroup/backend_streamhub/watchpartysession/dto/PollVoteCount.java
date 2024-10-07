package com.fdmgroup.backend_streamhub.watchpartysession.dto;

public class PollVoteCount {
    private long id;
    private long voteCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }
}
