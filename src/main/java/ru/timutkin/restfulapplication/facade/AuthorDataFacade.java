package ru.timutkin.restfulapplication.facade;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.timutkin.restfulapplication.dto.AuthorDTO;
import ru.timutkin.restfulapplication.dto.BookDTO;
import ru.timutkin.restfulapplication.service.AuthorService;

import ru.timutkin.restfulapplication.web.request.AuthorBookRequest;

import java.util.List;

@AllArgsConstructor
@Component
public class AuthorDataFacade {

    AuthorService authorService;

    public Long createAuthor(AuthorBookRequest authorBookRequest) {
        AuthorDTO authorDTO = authorBookRequest.getAuthorDto();
        List<BookDTO> bookDTOList = authorBookRequest.getBookDtoList();
        Long id;
        if (authorBookRequest.getBookDtoList()==null || authorBookRequest.getBookDtoList().isEmpty()){
            id = authorService.createAuthorWithoutBooks(authorDTO);
        }
        else {
            id = authorService.createAuthorWithBooks(authorDTO, bookDTOList);
        }
        return id;
    }
}
