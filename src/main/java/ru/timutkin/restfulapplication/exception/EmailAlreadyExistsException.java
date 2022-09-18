package ru.timutkin.restfulapplication.exception;

public class EmailAlreadyExistsException extends IllegalArgumentException{
    public EmailAlreadyExistsException(String s) {
        super(s);
    }
}
