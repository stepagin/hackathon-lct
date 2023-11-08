package ru.lct.itmoteam.taskservice.exception;

public class UserDoesNotExistException extends Exception {
    public UserDoesNotExistException(String message) {
        super(message);
    }
}
