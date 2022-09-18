package ru.timutkin.restfulapplication.mapper;

import org.mapstruct.Mapper;
import ru.timutkin.restfulapplication.dto.UserDTO;
import ru.timutkin.restfulapplication.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userEntityToUserDto(UserEntity userEntity);
    UserEntity userDtoToUserEntity(UserDTO userDTO);
}
