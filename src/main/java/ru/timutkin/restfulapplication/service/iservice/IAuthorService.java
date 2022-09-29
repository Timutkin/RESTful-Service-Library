package ru.timutkin.restfulapplication.service.iservice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.timutkin.restfulapplication.dto.AuthorDTO;
import ru.timutkin.restfulapplication.dto.BookDTO;
import ru.timutkin.restfulapplication.entity.AuthorEntity;
import ru.timutkin.restfulapplication.entity.BookEntity;
import ru.timutkin.restfulapplication.exception.IncorrectDataException;
import ru.timutkin.restfulapplication.mapper.AuthorMapper;
import ru.timutkin.restfulapplication.mapper.BookMapper;
import ru.timutkin.restfulapplication.repository.AuthorRepository;
import ru.timutkin.restfulapplication.repository.BookRepository;
import ru.timutkin.restfulapplication.service.AuthorService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class IAuthorService implements AuthorService {

    AuthorRepository authorRepository;

    AuthorMapper authorMapper;

    BookMapper bookMapper;

    BookRepository bookRepository;


    @Override
    public Long createAuthorWithoutBooks(AuthorDTO authorDTO) throws IncorrectDataException {
        AuthorEntity authorEntity = authorMapper.authorDtoToEntity(authorDTO);
        authorRepository.save(authorEntity);
        return authorEntity.getId();
    }

    @Override
    public Long createAuthorWithBooks(AuthorDTO authorDTO, List<BookDTO> list) throws IncorrectDataException {
        AuthorEntity authorEntity = authorMapper.authorDtoToEntity(authorDTO);
        authorEntity.setBooks(new HashSet<>());
        Set<BookEntity> unsavedBookEntity = list.stream()
                .map(bookMapper::bookDtoToBookEntity)
                .filter(bookEntity -> bookEntity.getId() == null).collect(Collectors.toSet());
        bookRepository.saveAll(unsavedBookEntity);
        unsavedBookEntity.forEach(el-> el.setAuthors(new HashSet<>()));
        unsavedBookEntity.forEach(authorEntity::addBook);
        authorRepository.save(authorEntity);
        return authorEntity.getId();
    }
}
