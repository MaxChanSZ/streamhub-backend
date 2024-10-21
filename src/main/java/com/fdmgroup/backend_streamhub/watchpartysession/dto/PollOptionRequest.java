package com.fdmgroup.backend_streamhub.watchpartysession.dto;

public class PollOptionRequest {
    private String description;
    private String fileName;
    private String value;
    private Long pollID;
    private Long pollOptionID;

    public Long getPollID() {
        return pollID;
    }

    public void setPollID(Long pollID) {
        this.pollID = pollID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getPollOptionID() {
        return pollOptionID;
    }

    public void setPollOptionID(Long pollOptionID) {
        this.pollOptionID = pollOptionID;
    }
}
