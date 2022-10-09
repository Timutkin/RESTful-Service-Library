package ru.timutkin.restfulapplication.service.iservice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.timutkin.restfulapplication.dto.AuthorDTO;
import ru.timutkin.restfulapplication.dto.BookDTO;
import ru.timutkin.restfulapplication.entity.AuthorEntity;
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
import ru.timutkin.restfulapplication.web.response.AuthorWithBookIdResponse;
import ru.timutkin.restfulapplication.web.response.AuthorWithBooksResponse;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import java.util.*;


@AllArgsConstructor
@Service
public class IAuthorService implements AuthorService {

    AuthorRepository authorRepository;

    AuthorMapper authorMapper;

    BookMapper bookMapper;

    BookRepository bookRepository;

    EntityManager entityManager;


    @Transactional
    @Override
    public AuthorWithBookIdResponse createAuthorWithoutBooks(AuthorDTO authorDTO) throws IncorrectDataException {
        if (authorRepository.existsByFirstNameAndAndLastNameAndAndPatronymicAndYearOfBirth(
                authorDTO.getFirstName(), authorDTO.getLastName(), authorDTO.getPatronymic(), authorDTO.getYearOfBirth()
        )){
            throw new DataAlreadyExistsException(ResponseConstant.AUTHOR_ALREADY_EXISTS);
        }
        AuthorEntity authorEntity = authorMapper.authorDtoToEntity(authorDTO);
        authorRepository.save(authorEntity);
        return AuthorWithBookIdResponse.builder()
                .authorDTO(authorMapper.authorEntityToAuthorDto(authorEntity))
                .build();
    }

    @Transactional
    @Override
    public AuthorWithBookIdResponse createAuthorWithBooks(AuthorDTO authorDTO, List<BookDTO> list) throws IncorrectDataException {
        if (authorRepository.existsByFirstNameAndAndLastNameAndAndPatronymicAndYearOfBirth(
                authorDTO.getFirstName(), authorDTO.getLastName(), authorDTO.getPatronymic(), authorDTO.getYearOfBirth()
        )){
            throw new DataAlreadyExistsException(ResponseConstant.AUTHOR_ALREADY_EXISTS);
        }

        AuthorWithBookIdResponse authorWithBookIdResponse = new AuthorWithBookIdResponse();
        AuthorEntity authorEntity = authorMapper.authorDtoToEntity(authorDTO);

        authorEntity.setBooks(new HashSet<>());

        list.stream()
                .map(bookMapper::bookDtoToBookEntity)
                .filter(bookEntity -> bookEntity.getId() == null)
                .peek(el-> el.setAuthors(new HashSet<>()))
                .peek(bookRepository::save)
                .peek(bookEntity -> authorWithBookIdResponse.addBookId(bookEntity.getId()))
                .forEach(authorEntity::addBook);

        authorRepository.save(authorEntity);

        list.stream()
                .map(bookMapper::bookDtoToBookEntity)
                .filter(bookEntity -> bookEntity.getId() != null)
                .peek(bookEntity -> authorWithBookIdResponse.addBookId(bookEntity.getId()))
                .map(bookEntity -> bookRepository.getBookEntityById(bookEntity.getId()))
                .forEach(authorEntity::addBook);

        authorRepository.saveAndFlush(authorEntity);

        authorWithBookIdResponse.setAuthorDTO(authorMapper.authorEntityToAuthorDto(authorEntity));

        return authorWithBookIdResponse;
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


    @Override
    public List<BookDTO> getBooksByAuthorId(Long id) {
        return getFullAuthorById(id).getBooks().stream()
                .map(bookMapper::bookEntityToBookDto)
                .toList();
    }

    @Override
    public AuthorWithBooksResponse getFullAuthorDataById(Long id) {
        AuthorEntity author = getFullAuthorById(id);
        return AuthorWithBooksResponse.builder()
                .authorDTO(authorMapper.authorEntityToAuthorDto(author))
                .bookDTOS(author.getBooks().stream()
                        .map(bookMapper::bookEntityToBookDto)
                        .toList()
                )
                .build();
    }


    @Transactional(readOnly = true)
    public AuthorEntity getFullAuthorById(Long id){
        EntityGraph<AuthorEntity> graph = entityManager.createEntityGraph(AuthorEntity.class);
        graph.addAttributeNodes("books");
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", graph);
        AuthorEntity author = entityManager.find(AuthorEntity.class, id, properties);
        if(author==null){
            throw new AuthorNotFoundException(String.format(ResponseConstant.AUTHOR_NOT_FOUND,id));
        }
        return author;
    }
}
