package ru.timutkin.restfulapplication.exception;

public class IncorrectDataException  extends IllegalArgumentException{
    public IncorrectDataException(String s) {
        super(s);
    }
}
