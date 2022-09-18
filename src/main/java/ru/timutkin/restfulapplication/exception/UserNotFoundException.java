package ru.timutkin.restfulapplication.exception;

public class UserNotFoundException extends IllegalArgumentException{
    public UserNotFoundException(String s) {
        super(s);
    }
}
