package ru.timutkin.restfulapplication.web.response.author;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.timutkin.restfulapplication.dto.AuthorDTO;
import ru.timutkin.restfulapplication.dto.BookDTO;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorWithBooksResponse implements AuthorResponse {
    AuthorDTO authorDTO;

    List<BookDTO> bookDTOS;
}
