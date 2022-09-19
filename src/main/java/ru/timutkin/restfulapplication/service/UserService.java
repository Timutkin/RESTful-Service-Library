package ru.timutkin.restfulapplication.service;

import ru.timutkin.restfulapplication.dto.UserDTO;
import ru.timutkin.restfulapplication.exception.EmailAlreadyExistsException;
import ru.timutkin.restfulapplication.exception.UserNotFoundException;

public interface UserService {
    Long createUser(UserDTO userDTO) throws EmailAlreadyExistsException;

    void deleteUser(Long id) throws UserNotFoundException;

    UserDTO getUserById(Long id) throws  UserNotFoundException;

    UserDTO updateUser(UserDTO updateUserDTO) throws UserNotFoundException, EmailAlreadyExistsException;
}
