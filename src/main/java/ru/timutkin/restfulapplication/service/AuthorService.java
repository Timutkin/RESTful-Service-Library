package ru.timutkin.restfulapplication.service;

import ru.timutkin.restfulapplication.dto.AuthorDTO;
import ru.timutkin.restfulapplication.dto.BookDTO;
import ru.timutkin.restfulapplication.exception.IncorrectDataException;
import ru.timutkin.restfulapplication.web.response.AuthorResponse;

import java.util.List;

public interface AuthorService {
    AuthorResponse createAuthorWithoutBooks(AuthorDTO authorDTO) throws IncorrectDataException;
    AuthorResponse createAuthorWithBooks(AuthorDTO authorDTO, List<BookDTO> bookDTOList) throws IncorrectDataException;

    void deleteAuthorById(Long id);

    AuthorResponse getAuthorById(Long id);

    void updateAuthor(AuthorDTO authorDTO);

    List<BookDTO> getBooksByAuthorId(Long id);

    AuthorResponse getFullAuthorDataById(Long id);
}
