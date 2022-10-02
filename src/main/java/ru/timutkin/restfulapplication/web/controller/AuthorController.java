package ru.timutkin.restfulapplication.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.timutkin.restfulapplication.facade.AuthorDataFacade;
import ru.timutkin.restfulapplication.service.AuthorService;
import ru.timutkin.restfulapplication.web.constant.WebConstant;
import ru.timutkin.restfulapplication.web.request.AuthorBookRequest;

@AllArgsConstructor
@RestController
@RequestMapping(value = WebConstant.VERSION_URL+"/authors",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AuthorController {

    AuthorService authorService;

    AuthorDataFacade authorDataFacade;

    @PostMapping
    public ResponseEntity<Long> createAuthor(@RequestBody AuthorBookRequest authorBookRequest){
        Long id = authorDataFacade.createAuthor(authorBookRequest);
        return ResponseEntity.ok(id);
    }

}
