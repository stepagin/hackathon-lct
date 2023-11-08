package ru.lct.itmoteam.taskservice.exception;

public class PasswordIncorrectException extends Exception {
    public PasswordIncorrectException(String message) {
        super(message);
    }
}
