package ru.timutkin.restfulapplication.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.timutkin.restfulapplication.enumeration.GenreOfLiterature;
import ru.timutkin.restfulapplication.web.response.book.BookResponse;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookDTO implements BookResponse {
    Long id;

    String title;

    Integer yearOfPrinting;

    Integer numberOfParts;

    Integer countOfPage;

    GenreOfLiterature genreOfLiterature;

}
