package ru.timutkin.restfulapplication.service;

import ru.timutkin.restfulapplication.dto.UserDTO;
import ru.timutkin.restfulapplication.exception.EmailAlreadyExistsException;
import ru.timutkin.restfulapplication.exception.UserNotFoundException;
import ru.timutkin.restfulapplication.web.request.UserBookRequest;
import ru.timutkin.restfulapplication.web.response.user.UserBookResponse;

public interface UserService {
    Long createUser(UserDTO userDTO) throws EmailAlreadyExistsException;

    void deleteUser(Long id) throws UserNotFoundException;

    UserDTO getUserById(Long id) throws  UserNotFoundException;

    void updateUser(UserDTO updateUserDTO) throws UserNotFoundException, EmailAlreadyExistsException;

    UserBookResponse bindBookToUser(UserBookRequest userBookRequest);
}
