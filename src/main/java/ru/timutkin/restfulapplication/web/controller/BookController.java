package ru.timutkin.restfulapplication.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.timutkin.restfulapplication.dto.BookDTO;
import ru.timutkin.restfulapplication.exception.IncorrectDataException;
import ru.timutkin.restfulapplication.facade.BookDataFacade;
import ru.timutkin.restfulapplication.service.BookService;
import ru.timutkin.restfulapplication.web.constant.ResponseConstant;
import ru.timutkin.restfulapplication.web.constant.WebConstant;
import ru.timutkin.restfulapplication.web.request.BookAuthorRequest;
import ru.timutkin.restfulapplication.web.response.AuthorResponse;
import ru.timutkin.restfulapplication.web.response.BookResponse;
import ru.timutkin.restfulapplication.web.response.ErrorResponse;

@AllArgsConstructor
@RestController
@RequestMapping(value = WebConstant.VERSION_URL+"/books",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class BookController {

    BookService bookService;

    BookDataFacade bookDataFacade;


    @PostMapping
    @Operation(summary = "Creates a book", description = "",
            responses = {
                    @ApiResponse( responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Long.class)
                            )),
                    @ApiResponse( responseCode = "409",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            ))})
    public ResponseEntity<BookResponse> createBook(@RequestBody BookAuthorRequest bookAuthorRequest){

        BookResponse bookResponse = bookDataFacade.createBook(bookAuthorRequest);

        return ResponseEntity.ok(bookResponse);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Gets a book",
            responses = {
                    @ApiResponse( responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BookDTO.class)
                            )),
                    @ApiResponse(description = ResponseConstant.BOOK_WITH_ID_D_NOT_FOUND, responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)))})
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id){

        BookDTO bookDTO =  bookService.getBookById(id);

        return ResponseEntity.ok(bookDTO);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a book by id",
            responses = {
                    @ApiResponse( responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Long.class)
                            )),
                    @ApiResponse(description = ResponseConstant.BOOK_WITH_ID_D_NOT_FOUND, responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)))})
    public ResponseEntity<Long> deleteBookById(@PathVariable Long id){
        bookService.deleteBookById(id);
        return ResponseEntity.ok(id);
    }
    @PutMapping
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a book by id",
            responses = {
                    @ApiResponse( responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Long.class)
                            )),
                    @ApiResponse(description = ResponseConstant.BOOK_WITH_ID_D_NOT_FOUND, responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)))})
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO bookDTO){
        if (bookDTO.getCountOfPage() > 4000) {
            throw new IncorrectDataException(ResponseConstant.INCORRECT_COUNT_OF_PAGE);
        }
        bookService.updateBook(bookDTO);
        return ResponseEntity.ok(bookDTO);
    }


}
