package com.example.chat.web;

/**
 * Created by rui on 09/07/2019
 */
public class Flash {
    private String message;
    private Status status;

    public String getMessage() {
        return message;
    }

    public Status getStatus() {
        return status;
    }

    public Flash(String message, Status status) {
        this.message = message;
        this.status = status;
    }

    public enum Status {
        SUCCESS,
        FAILURE;

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
