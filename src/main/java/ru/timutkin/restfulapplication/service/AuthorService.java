package ru.timutkin.restfulapplication.service;

import ru.timutkin.restfulapplication.dto.AuthorDTO;
import ru.timutkin.restfulapplication.dto.BookDTO;
import ru.timutkin.restfulapplication.exception.IncorrectDataException;

import java.util.List;

public interface AuthorService {
    Long createAuthorWithoutBooks(AuthorDTO authorDTO) throws IncorrectDataException;
    Long createAuthorWithBooks(AuthorDTO authorDTO, List<BookDTO> bookDTOList) throws IncorrectDataException;
}
