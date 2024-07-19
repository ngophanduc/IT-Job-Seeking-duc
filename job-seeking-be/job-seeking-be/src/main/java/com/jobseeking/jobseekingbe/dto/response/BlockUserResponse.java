package com.jobseeking.jobseekingbe.dto.response;

public class BlockUserResponse {
    private String message;
    private boolean success;

    public BlockUserResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

