package ru.timutkin.restfulapplication.web.response.user;


import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.timutkin.restfulapplication.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserBookResponse {
    UserDTO userDTO;
    List<String> listOfTitleBooks = new ArrayList<>();

    public void addTittle(String title){
        listOfTitleBooks.add(title);
    }
}
