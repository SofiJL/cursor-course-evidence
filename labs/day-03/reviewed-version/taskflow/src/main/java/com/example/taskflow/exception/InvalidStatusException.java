package com.example.taskflow.exception;

public class InvalidStatusException extends RuntimeException {

    public InvalidStatusException(String status) {
        super("Invalid status: " + status);
    }
}
