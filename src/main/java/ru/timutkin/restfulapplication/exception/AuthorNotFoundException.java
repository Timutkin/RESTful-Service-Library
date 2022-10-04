package ru.timutkin.restfulapplication.exception;

public class AuthorNotFoundException extends IllegalArgumentException{
    public AuthorNotFoundException(String s) {
        super(s);
    }
}
