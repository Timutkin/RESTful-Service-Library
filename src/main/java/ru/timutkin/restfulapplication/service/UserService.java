package ru.timutkin.restfulapplication.service;

import ru.timutkin.restfulapplication.dto.UserDTO;
import ru.timutkin.restfulapplication.exception.EmailAlreadyExistsException;

public interface UserService {
    Long createUser(UserDTO userDTO) throws EmailAlreadyExistsException;

    void deleteUser(Long id);

}
