package ru.timutkin.restfulapplication.exception;

public class DataAlreadyExistsException extends IllegalArgumentException{
    public DataAlreadyExistsException(String s) {
        super(s);
    }
}
