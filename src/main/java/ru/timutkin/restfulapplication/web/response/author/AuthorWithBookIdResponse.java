package ru.timutkin.restfulapplication.web.response.author;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.timutkin.restfulapplication.dto.AuthorDTO;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorWithBookIdResponse implements AuthorResponse {
    AuthorDTO authorDTO;
    List<Long> booksId = new ArrayList<>();

    public void addBookId(Long id){
        booksId.add(id);
    }
}
