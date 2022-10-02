package ru.timutkin.restfulapplication.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.timutkin.restfulapplication.exception.IncorrectDataException;
import ru.timutkin.restfulapplication.facade.AuthorDataFacade;
import ru.timutkin.restfulapplication.service.AuthorService;
import ru.timutkin.restfulapplication.web.constant.ResponseConstant;
import ru.timutkin.restfulapplication.web.constant.SwaggerDescription;
import ru.timutkin.restfulapplication.web.constant.WebConstant;
import ru.timutkin.restfulapplication.web.request.AuthorBookRequest;
import ru.timutkin.restfulapplication.web.response.AuthorResponse;
import ru.timutkin.restfulapplication.web.response.ErrorResponse;

@AllArgsConstructor
@RestController
@RequestMapping(value = WebConstant.VERSION_URL+"/authors",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AuthorController {

    AuthorService authorService;

    AuthorDataFacade authorDataFacade;

    @Operation(summary = "Creates a author", description = SwaggerDescription.CREATE_AUTHOR,
            responses = {
                    @ApiResponse( responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AuthorResponse.class)
                            ))})
    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@RequestBody AuthorBookRequest authorBookRequest){
        AuthorResponse authorResponse = authorDataFacade.createAuthor(authorBookRequest);
        return ResponseEntity.ok(authorResponse);
    }

}
