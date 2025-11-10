package ru.khmelevskoy.exception;

public class CustomException extends RuntimeException {
    public CustomException(Throwable cause) {
        super(cause);
    }

    public CustomException(String message) {
        super(message);
    }
}