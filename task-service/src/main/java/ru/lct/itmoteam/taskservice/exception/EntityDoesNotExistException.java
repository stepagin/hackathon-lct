package ru.lct.itmoteam.taskservice.exception;

public class EntityDoesNotExistException extends Exception {
    public EntityDoesNotExistException(String message) {
        super(message);
    }
}
