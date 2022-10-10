package ru.timutkin.restfulapplication.facade;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.timutkin.restfulapplication.dto.AuthorDTO;
import ru.timutkin.restfulapplication.dto.BookDTO;
import ru.timutkin.restfulapplication.service.BookService;
import ru.timutkin.restfulapplication.web.request.BookAuthorRequest;
import ru.timutkin.restfulapplication.web.response.book.BookResponse;
import ru.timutkin.restfulapplication.web.response.book.BookWithAuthorIdResponse;

import java.util.List;

@AllArgsConstructor
@Component
public class BookDataFacade {

    BookService bookService;

    public BookResponse createBook(BookAuthorRequest bookAuthorRequest) {
        BookDTO bookDTO = bookAuthorRequest.getBookDto();
        List<AuthorDTO> authorDtoList = bookAuthorRequest.getAuthorDtoList();
        if (authorDtoList == null || authorDtoList.isEmpty()){
            return bookService.createBookWithoutAuthors(bookDTO);
        }
        else {
            return bookService.createBookWithAuthors(bookDTO, authorDtoList);
        }
    }

    public BookResponse getBookById(Long id, Boolean fullLoad){
        BookResponse response;
        if (fullLoad == null || !fullLoad){
           return bookService.getBookById(id);
        }
        else {
            return bookService.getFullDataBookById(id);
        }
    }
}
