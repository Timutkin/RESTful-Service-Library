package ru.timutkin.restfulapplication.service;

import ru.timutkin.restfulapplication.dto.BookDTO;
import ru.timutkin.restfulapplication.exception.BookNotFoundException;

public interface BookService {

    Long createBook(BookDTO bookDTO);

    BookDTO getBookById(Long id) throws BookNotFoundException;
}
