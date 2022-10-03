package ru.timutkin.restfulapplication.web.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.timutkin.restfulapplication.dto.BookDTO;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponse {
    BookDTO bookDTO;
    List<Long> authorsId = new ArrayList<>();

    public void addAuthorById(Long id){
        authorsId.add(id);
    }
}
