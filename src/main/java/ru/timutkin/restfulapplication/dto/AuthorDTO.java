package ru.timutkin.restfulapplication.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.timutkin.restfulapplication.enumeration.GenreOfLiterature;

import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class AuthorDTO {
     Long id;

     String firstName;

     String lastName;

     String patronymic;

     LocalDate yearOfBirth;


}
