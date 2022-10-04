package ru.timutkin.restfulapplication.service.iservice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.timutkin.restfulapplication.dto.AuthorDTO;
import ru.timutkin.restfulapplication.dto.BookDTO;
import ru.timutkin.restfulapplication.entity.AuthorEntity;
import ru.timutkin.restfulapplication.entity.BookEntity;
import ru.timutkin.restfulapplication.exception.AuthorNotFoundException;
import ru.timutkin.restfulapplication.exception.DataAlreadyExistsException;
import ru.timutkin.restfulapplication.exception.IncorrectDataException;
import ru.timutkin.restfulapplication.mapper.AuthorMapper;
import ru.timutkin.restfulapplication.mapper.BookMapper;
import ru.timutkin.restfulapplication.repository.AuthorRepository;
import ru.timutkin.restfulapplication.repository.BookRepository;
import ru.timutkin.restfulapplication.service.AuthorService;
import ru.timutkin.restfulapplication.web.constant.ResponseConstant;
import ru.timutkin.restfulapplication.web.response.AuthorResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class IAuthorService implements AuthorService {

    AuthorRepository authorRepository;

    AuthorMapper authorMapper;

    BookMapper bookMapper;

    BookRepository bookRepository;


    @Transactional
    @Override
    public AuthorResponse createAuthorWithoutBooks(AuthorDTO authorDTO) throws IncorrectDataException {
        if (authorRepository.existsByFirstNameAndAndLastNameAndAndPatronymicAndYearOfBirth(
                authorDTO.getFirstName(), authorDTO.getLastName(), authorDTO.getPatronymic(), authorDTO.getYearOfBirth()
        )){
            throw new DataAlreadyExistsException(ResponseConstant.AUTHOR_ALREADY_EXISTS);
        }
        AuthorEntity authorEntity = authorMapper.authorDtoToEntity(authorDTO);
        authorRepository.save(authorEntity);
        return AuthorResponse.builder()
                .authorDTO(authorMapper.authorEntityToAuthorDto(authorEntity))
                .build();
    }

    @Transactional
    @Override
    public AuthorResponse createAuthorWithBooks(AuthorDTO authorDTO, List<BookDTO> list) throws IncorrectDataException {
        if (authorRepository.existsByFirstNameAndAndLastNameAndAndPatronymicAndYearOfBirth(
                authorDTO.getFirstName(), authorDTO.getLastName(), authorDTO.getPatronymic(), authorDTO.getYearOfBirth()
        )){
            throw new DataAlreadyExistsException(ResponseConstant.AUTHOR_ALREADY_EXISTS);
        }

        AuthorResponse authorResponse = new AuthorResponse();
        AuthorEntity authorEntity = authorMapper.authorDtoToEntity(authorDTO);

        authorEntity.setBooks(new HashSet<>());

        list.stream()
                .map(bookMapper::bookDtoToBookEntity)
                .filter(bookEntity -> bookEntity.getId() == null)
                .peek(el-> el.setAuthors(new HashSet<>()))
                .peek(bookRepository::save)
                .peek(bookEntity -> authorResponse.addBookId(bookEntity.getId()))
                .forEach(authorEntity::addBook);

        authorRepository.save(authorEntity);

        list.stream()
                .map(bookMapper::bookDtoToBookEntity)
                .filter(bookEntity -> bookEntity.getId() != null)
                .peek(bookEntity -> authorResponse.addBookId(bookEntity.getId()))
                .map(bookEntity -> bookRepository.getBookEntityById(bookEntity.getId()))
                .forEach(authorEntity::addBook);

        authorRepository.saveAndFlush(authorEntity);

        authorResponse.setAuthorDTO(authorMapper.authorEntityToAuthorDto(authorEntity));

        return authorResponse;
    }

    @Transactional
    @Override
    public void deleteAuthorById(Long id) {
        if (!authorRepository.existsById(id)){
            throw new AuthorNotFoundException(String.format(ResponseConstant.AUTHOR_NOT_FOUND,id));
        }
        authorRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public AuthorDTO getAuthorById(Long id) {
        Optional<AuthorEntity> authorEntity = authorRepository.findById(id);
        if (authorEntity.isEmpty()){
            throw new AuthorNotFoundException(ResponseConstant.AUTHOR_NOT_FOUND);
        }
        return authorMapper.authorEntityToAuthorDto(authorEntity.get());
    }

    @Transactional()
    @Override
    public void updateAuthor(AuthorDTO authorDTO) {
        Optional<AuthorEntity>  authorEntity = authorRepository.findById(authorDTO.getId());
        if (authorEntity.isEmpty()){
            throw new AuthorNotFoundException(String.format(ResponseConstant.AUTHOR_NOT_FOUND,authorDTO.getId()));
        }
        AuthorEntity updatedAuthorEntity = authorMapper.authorDtoToEntity(authorDTO);
        updatedAuthorEntity.setBooks(authorEntity.get().getBooks());
        authorRepository.save(updatedAuthorEntity);
    }
}
