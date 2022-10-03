package ru.timutkin.restfulapplication.service;

import ru.timutkin.restfulapplication.dto.AuthorDTO;
import ru.timutkin.restfulapplication.dto.BookDTO;
import ru.timutkin.restfulapplication.exception.BookNotFoundException;
import ru.timutkin.restfulapplication.web.response.BookResponse;

import java.util.List;

public interface BookService {

    BookResponse createBookWithoutAuthors(BookDTO bookDTO);

    BookDTO getBookById(Long id) throws BookNotFoundException;

    void deleteBookById(Long id) throws BookNotFoundException;

    void updateBook(BookDTO bookDTO) ;

    BookResponse createBookWithAuthors(BookDTO bookDTO, List<AuthorDTO> authorDTOList);
}
