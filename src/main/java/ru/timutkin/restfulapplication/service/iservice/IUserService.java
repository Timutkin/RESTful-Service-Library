package ru.timutkin.restfulapplication.service.iservice;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.timutkin.restfulapplication.dto.UserDTO;
import ru.timutkin.restfulapplication.entity.UserEntity;
import ru.timutkin.restfulapplication.exception.EmailAlreadyExistsException;
import ru.timutkin.restfulapplication.exception.UserNotFoundException;
import ru.timutkin.restfulapplication.mapper.UserMapper;
import ru.timutkin.restfulapplication.repository.UserRepository;
import ru.timutkin.restfulapplication.service.UserService;

@Slf4j
@Service
@AllArgsConstructor
public class IUserService implements UserService {

    UserRepository userRepository;

    UserMapper userMapper;

    @Override
    public Long createUser(UserDTO userDTO) {

        if (userRepository.existsByEmail(userDTO.getEmail())){
            throw new EmailAlreadyExistsException(
                    String.format("User with email %s already exists",userDTO.getEmail()));
        }

        UserEntity userEntity = userMapper.userDtoToUserEntity(userDTO);

        userRepository.save(userEntity);

        return userEntity.getId();
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)){
            throw new UserNotFoundException(String.format("User with id = %s not found",id));
        }
        userRepository.deleteById(id);
    }
}
