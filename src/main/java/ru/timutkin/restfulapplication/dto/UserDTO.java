package ru.timutkin.restfulapplication.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    String email;
    String firstName;
    String lastName;
}
