package ru.timutkin.restfulapplication.web.response.book;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.timutkin.restfulapplication.dto.AuthorDTO;
import ru.timutkin.restfulapplication.dto.BookDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookWithAuthorsResponse implements BookResponse{
    BookDTO bookDTO;

    List<AuthorDTO> authorDTOList;
}
