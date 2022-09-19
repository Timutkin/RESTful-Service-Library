package ru.timutkin.restfulapplication.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    Long id;

    String title;

    String author;

    Integer yearOfPrinting;

    Integer numberOfParts;

    Integer countOfPage;

}
