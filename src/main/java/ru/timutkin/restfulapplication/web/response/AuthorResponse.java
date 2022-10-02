package ru.timutkin.restfulapplication.web.response;

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
public class AuthorResponse {
    AuthorDTO authorDTO;
    List<Long> bookId = new ArrayList<>();


    public void addBookId(Long id){
        bookId.add(id);
    }
}
