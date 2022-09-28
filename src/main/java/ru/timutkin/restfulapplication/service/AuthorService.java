package ru.timutkin.restfulapplication.service;

import ru.timutkin.restfulapplication.dto.AuthorDTO;
import ru.timutkin.restfulapplication.exception.IncorrectDataException;

public interface AuthorService {
    Long createAuthor(AuthorDTO authorDTO) throws IncorrectDataException;
}
