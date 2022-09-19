package ru.timutkin.restfulapplication.exception;

public class BookNotFoundException extends IllegalArgumentException{
    public BookNotFoundException(String s) {
        super(s);
    }
}
