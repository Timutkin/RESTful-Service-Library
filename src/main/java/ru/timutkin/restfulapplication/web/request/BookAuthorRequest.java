package ru.timutkin.restfulapplication.web.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.timutkin.restfulapplication.dto.AuthorDTO;
import ru.timutkin.restfulapplication.dto.BookDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookAuthorRequest {
    BookDTO bookDto;
    List<AuthorDTO> authorDtoList;
}
