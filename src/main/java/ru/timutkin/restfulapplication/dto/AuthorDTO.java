package ru.timutkin.restfulapplication.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.timutkin.restfulapplication.web.response.author.AuthorResponse;

import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO implements AuthorResponse {
     Long id;

     String firstName;

     String lastName;

     String patronymic;

     LocalDate yearOfBirth;


}
