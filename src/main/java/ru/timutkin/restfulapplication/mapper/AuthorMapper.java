package ru.timutkin.restfulapplication.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.timutkin.restfulapplication.dto.AuthorDTO;
import ru.timutkin.restfulapplication.entity.AuthorEntity;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDTO authorEntityToAuthorDto(AuthorEntity authorEntity);

    AuthorEntity authorDtoToEntity(AuthorDTO authorDTO);

}
