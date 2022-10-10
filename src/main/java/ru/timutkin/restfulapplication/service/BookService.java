package ru.timutkin.restfulapplication.service;

import ru.timutkin.restfulapplication.dto.AuthorDTO;
import ru.timutkin.restfulapplication.dto.BookDTO;
import ru.timutkin.restfulapplication.exception.BookNotFoundException;
import ru.timutkin.restfulapplication.web.response.book.BookResponse;
import ru.timutkin.restfulapplication.web.response.book.BookWithAuthorIdResponse;

import java.util.List;

public interface BookService {

    BookResponse createBookWithoutAuthors(BookDTO bookDTO);

    BookResponse getBookById(Long id) throws BookNotFoundException;

    void deleteBookById(Long id) throws BookNotFoundException;

    void updateBook(BookDTO bookDTO) ;

    BookWithAuthorIdResponse createBookWithAuthors(BookDTO bookDTO, List<AuthorDTO> authorDTOList);

    BookResponse getFullDataBookById(Long id);

    List<BookDTO> getListOfBooks(Integer numberOfPage);
}
