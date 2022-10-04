package ru.timutkin.restfulapplication.web.handler;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.timutkin.restfulapplication.exception.*;
import ru.timutkin.restfulapplication.web.response.ErrorResponse;
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {EmailAlreadyExistsException.class})
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistException(@NonNull final EmailAlreadyExistsException exc) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(createErrorMessage(exc)));
    }

    @ExceptionHandler(value = {DataAlreadyExistsException.class})
    public ResponseEntity<ErrorResponse> handleDataAlreadyExistException(@NonNull final DataAlreadyExistsException exc) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(createErrorMessage(exc)));
    }

    @ExceptionHandler(value = {UserNotFoundException.class, BookNotFoundException.class, AuthorNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(@NonNull final IllegalArgumentException exc) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(createErrorMessage(exc)));
    }

    @ExceptionHandler(value = {IncorrectDataException.class})
    public ResponseEntity<ErrorResponse> handleIncorrectDataException(@NonNull final IncorrectDataException exc) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(createErrorMessage(exc)));
    }


    private String createErrorMessage(Exception exception) {
        log.error(ExceptionHandlerUtils.buildErrorMessage(exception));
        return exception.getMessage();
    }
}
