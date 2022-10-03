package ru.timutkin.restfulapplication.facade;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.timutkin.restfulapplication.dto.AuthorDTO;
import ru.timutkin.restfulapplication.dto.BookDTO;
import ru.timutkin.restfulapplication.service.BookService;
import ru.timutkin.restfulapplication.web.request.BookAuthorRequest;
import ru.timutkin.restfulapplication.web.response.BookResponse;

import java.util.List;

@AllArgsConstructor
@Component
public class BookDataFacade {

    BookService bookService;

    public BookResponse createBook(BookAuthorRequest bookAuthorRequest) {
        BookDTO bookDTO = bookAuthorRequest.getBookDto();
        List<AuthorDTO> authorDtoList = bookAuthorRequest.getAuthorDtoList();
        BookResponse response;
        if (authorDtoList == null || authorDtoList.isEmpty()){
            response = bookService.createBookWithoutAuthors(bookDTO);
        }
        else {
            response = bookService.createBookWithAuthors(bookDTO, authorDtoList);
        }
        return response;
    }
}
