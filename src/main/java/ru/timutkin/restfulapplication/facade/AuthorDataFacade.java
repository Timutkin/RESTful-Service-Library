package ru.timutkin.restfulapplication.facade;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.timutkin.restfulapplication.dto.AuthorDTO;
import ru.timutkin.restfulapplication.dto.BookDTO;
import ru.timutkin.restfulapplication.service.AuthorService;

import ru.timutkin.restfulapplication.web.request.AuthorBookRequest;
import ru.timutkin.restfulapplication.web.response.author.AuthorResponse;

import java.util.List;

@AllArgsConstructor
@Component
public class AuthorDataFacade {

    AuthorService authorService;

    public AuthorResponse createAuthor(AuthorBookRequest authorBookRequest) {
        AuthorDTO authorDTO = authorBookRequest.getAuthorDto();
        List<BookDTO> bookDTOList = authorBookRequest.getBookDtoList();
        AuthorResponse response;
        if (authorBookRequest.getBookDtoList()==null || authorBookRequest.getBookDtoList().isEmpty()){
            response = authorService.createAuthorWithoutBooks(authorDTO);
        }
        else {
            response = authorService.createAuthorWithBooks(authorDTO, bookDTOList);
        }
        return response;
    }

    public AuthorResponse getAuthor(Long id, Boolean fullLoad){
        AuthorResponse authorResponse;
        if (fullLoad == null || !fullLoad){
            authorResponse = authorService.getAuthorById(id);
        }
        else {
            authorResponse = authorService.getFullAuthorDataById(id);
        }
        return authorResponse;
    }
}
