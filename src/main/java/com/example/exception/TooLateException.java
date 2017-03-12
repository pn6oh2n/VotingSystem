package com.example.exception;

public class TooLateException extends RuntimeException {
    public TooLateException(String message) {
        super(message);
    }
}
