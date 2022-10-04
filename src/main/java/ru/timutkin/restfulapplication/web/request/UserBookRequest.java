package ru.timutkin.restfulapplication.web.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserBookRequest {
    Long userId;
    List<Long> booksId;
}
