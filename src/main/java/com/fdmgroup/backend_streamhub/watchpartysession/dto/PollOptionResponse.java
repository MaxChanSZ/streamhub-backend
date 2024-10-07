package com.fdmgroup.backend_streamhub.watchpartysession.dto;

public class PollOptionResponse {
    private long pollOptionId;
    private String value;
    private String description;
    private String imageUrl;
    private long voteCount;

    public long getPollOptionId() {
        return pollOptionId;
    }

    public void setPollOptionId(long pollOptionId) {
        this.pollOptionId = pollOptionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
