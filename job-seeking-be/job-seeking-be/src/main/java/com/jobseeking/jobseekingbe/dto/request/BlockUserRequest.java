package com.jobseeking.jobseekingbe.dto.request;
public class BlockUserRequest {
    private String userId;

    public BlockUserRequest(){

    }

    public BlockUserRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

