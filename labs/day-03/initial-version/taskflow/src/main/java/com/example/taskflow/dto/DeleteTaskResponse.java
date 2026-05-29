package com.example.taskflow.dto;

public class DeleteTaskResponse {

    private String message;

    public DeleteTaskResponse() {
    }

    public DeleteTaskResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
