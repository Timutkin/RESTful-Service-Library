package ru.timutkin.restfulapplication.mapper;

import org.mapstruct.Mapper;
import ru.timutkin.restfulapplication.dto.BookDTO;
import ru.timutkin.restfulapplication.entity.BookEntity;


@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO bookEntityToBookDto(BookEntity bookEntity);
    BookEntity bookDtoToBookEntity(BookDTO bookDTO);
}
