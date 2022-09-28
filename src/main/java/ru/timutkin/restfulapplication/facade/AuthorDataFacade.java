package ru.timutkin.restfulapplication.facade;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.timutkin.restfulapplication.web.request.AuthorBookRequest;

@AllArgsConstructor
@Component
public class AuthorDataFacade {


    public Long createAuthor(AuthorBookRequest authorBookRequest) {
        if (authorBookRequest.getBookDtoList().isEmpty() || authorBookRequest.getBookDtoList()==null){

        }
        return 1L;
    }
}
