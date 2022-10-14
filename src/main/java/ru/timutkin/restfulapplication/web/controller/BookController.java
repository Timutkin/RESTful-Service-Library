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
import ru.timutkin.restfulapplication.entity.BookEntity;
import ru.timutkin.restfulapplication.exception.IncorrectDataException;
import ru.timutkin.restfulapplication.facade.BookDataFacade;
import ru.timutkin.restfulapplication.repository.BookRepository;
import ru.timutkin.restfulapplication.service.BookService;
import ru.timutkin.restfulapplication.web.constant.ResponseConstant;
import ru.timutkin.restfulapplication.web.constant.WebConstant;
import ru.timutkin.restfulapplication.web.request.BookAuthorRequest;
import ru.timutkin.restfulapplication.web.response.book.BookResponse;
import ru.timutkin.restfulapplication.web.response.ErrorResponse;
import ru.timutkin.restfulapplication.web.response.book.BookWithAuthorIdResponse;
import ru.timutkin.restfulapplication.web.response.book.BookWithAuthorsResponse;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = WebConstant.VERSION_URL+"/books",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class BookController {

    BookService bookService;

    BookDataFacade bookDataFacade;



    @PostMapping
    @Operation(summary = "Create a book", description = "",
            responses = {
                    @ApiResponse( responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BookWithAuthorIdResponse.class)
                            )),
                    @ApiResponse( responseCode = "409",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)
                            ))})
    public ResponseEntity<BookResponse> createBook(@RequestBody BookAuthorRequest bookAuthorRequest){

        BookResponse bookWithAuthorIdResponse = bookDataFacade.createBook(bookAuthorRequest);

        return ResponseEntity.ok(bookWithAuthorIdResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a book",
            responses = {
                    @ApiResponse( responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BookDTO.class)
                            )),
                    @ApiResponse( responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = BookWithAuthorsResponse.class)
                            )),
                    @ApiResponse(description = ResponseConstant.BOOK_WITH_ID_D_NOT_FOUND, responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)))})
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id, @RequestParam(name = "fullLoad", required = false) Boolean fullLoad){

        BookResponse bookResponse = bookDataFacade.getBookById(id, fullLoad);

        return ResponseEntity.ok(bookResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a book by id",
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
    @Operation(summary = "Update a book by id",
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


    @GetMapping("/list/{numberOfPage}")
    @Operation(summary = "Get page of list of books by number of page", description = "The list of books wil be sorted by title",
            responses = {
                    @ApiResponse( responseCode = "200",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = List.class)
                            )),
                    @ApiResponse(description = ResponseConstant.BOOK_WITH_ID_D_NOT_FOUND, responseCode = "400",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorResponse.class)))})
    public ResponseEntity<List<BookDTO>> getListOfBooks(@PathVariable Integer numberOfPage){
        List<BookDTO> bookDTOList = bookService.getListOfBooks(numberOfPage);
        return ResponseEntity.ok(bookDTOList);
    }

    @GetMapping()
    public ResponseEntity<List<BookDTO>> getListOfBooksByTitle(@RequestParam(name = "title") String title){
        List<BookDTO> bookDTOList = bookService.getListOfBooksByTitle(title);
        return ResponseEntity.ok(bookDTOList);
    }


}
