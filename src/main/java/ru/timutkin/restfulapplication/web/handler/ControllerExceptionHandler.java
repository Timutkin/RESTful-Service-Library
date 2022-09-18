package ru.timutkin.restfulapplication.web.handler;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.timutkin.restfulapplication.exception.EmailAlreadyExistsException;
import ru.timutkin.restfulapplication.exception.UserNotFoundException;
import ru.timutkin.restfulapplication.web.response.Response;
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {EmailAlreadyExistsException.class})
    public ResponseEntity<Response> handleEmailAlreadyExistException(@NonNull final EmailAlreadyExistsException exc) {
        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new Response(createErrorMessage(exc)));
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Response> handleNotFoundException(@NonNull final UserNotFoundException exc) {
        log.error(exc.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new Response(createErrorMessage(exc)));
    }

    private String createErrorMessage(Exception exception) {
        final String message = exception.getMessage();
        return message;
    }
}
