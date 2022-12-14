package ru.timutkin.restfulapplication.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.timutkin.restfulapplication.dto.AuthorDTO;
import ru.timutkin.restfulapplication.dto.BookDTO;
import ru.timutkin.restfulapplication.facade.AuthorDataFacade;
import ru.timutkin.restfulapplication.service.AuthorService;
import ru.timutkin.restfulapplication.web.constant.SwaggerDescription;
import ru.timutkin.restfulapplication.web.constant.WebConstant;
import ru.timutkin.restfulapplication.web.request.AuthorBookRequest;
import ru.timutkin.restfulapplication.web.response.author.AuthorResponse;
import ru.timutkin.restfulapplication.web.response.author.AuthorWithBookIdResponse;
import ru.timutkin.restfulapplication.web.response.ErrorResponse;
import ru.timutkin.restfulapplication.web.response.book.BookResponse;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = WebConstant.VERSION_URL+"/authors",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AuthorController {

    AuthorService authorService;

    AuthorDataFacade authorDataFacade;

    @Operation(summary = "Create an author", description = SwaggerDescription.CREATE_AUTHOR,
            responses = {
                    @ApiResponse( responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AuthorWithBookIdResponse.class)
                            )),
                    @ApiResponse( responseCode = "409",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            ))})
    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@RequestBody AuthorBookRequest authorBookRequest){
        AuthorResponse authorWithBookIdResponse = authorDataFacade.createAuthor(authorBookRequest);
        return ResponseEntity.ok(authorWithBookIdResponse);
    }

    @Operation(summary = "Get an author", description = SwaggerDescription.CREATE_AUTHOR,
            responses = {
                    @ApiResponse( responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AuthorWithBookIdResponse.class)
                            )),
                    @ApiResponse( responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            ))})
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthorById(@PathVariable Long id, @RequestParam(name = "fullLoad", required = false) Boolean fullLoad){
        AuthorResponse authorResponse = authorDataFacade.getAuthor(id, fullLoad);
        return ResponseEntity.ok(authorResponse);
    }

    @Operation(summary = "Update an author", description = SwaggerDescription.CREATE_AUTHOR,
            responses = {
                    @ApiResponse( responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AuthorDTO.class)
                            )),
                    @ApiResponse( responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            ))})
    @PutMapping
    public ResponseEntity<AuthorDTO> updateAuthor(@RequestBody AuthorDTO authorDTO){
        authorService.updateAuthor(authorDTO);
        return ResponseEntity.ok(authorDTO);
    }

    @Operation(summary = "Delete an author", description = SwaggerDescription.CREATE_AUTHOR,
            responses = {
                    @ApiResponse( responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Long.class)
                            )),
                    @ApiResponse( responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            ))})
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthorById(id);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<List<BookDTO>> getBooksByAuthorId(@PathVariable Long id){
        List<BookDTO> bookDTOList = authorService.getBooksByAuthorId(id);
        return ResponseEntity.ok(bookDTOList);
    }


}
