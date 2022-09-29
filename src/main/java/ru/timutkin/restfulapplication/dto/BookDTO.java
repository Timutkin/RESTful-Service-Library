package ru.timutkin.restfulapplication.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.timutkin.restfulapplication.enumeration.GenreOfLiterature;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookDTO {
    Long id;

    String title;

    Integer yearOfPrinting;

    Integer numberOfParts;

    Integer countOfPage;

    GenreOfLiterature genreOfLiterature;

}
