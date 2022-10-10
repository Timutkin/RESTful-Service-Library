package ru.timutkin.restfulapplication.service.iservice;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.timutkin.restfulapplication.dto.AuthorDTO;
import ru.timutkin.restfulapplication.dto.BookDTO;
import ru.timutkin.restfulapplication.entity.AuthorEntity;
import ru.timutkin.restfulapplication.entity.BookEntity;
import ru.timutkin.restfulapplication.exception.BookNotFoundException;
import ru.timutkin.restfulapplication.exception.DataAlreadyExistsException;
import ru.timutkin.restfulapplication.exception.IncorrectDataException;
import ru.timutkin.restfulapplication.mapper.AuthorMapper;
import ru.timutkin.restfulapplication.mapper.BookMapper;
import ru.timutkin.restfulapplication.repository.AuthorRepository;
import ru.timutkin.restfulapplication.repository.BookRepository;
import ru.timutkin.restfulapplication.service.BookService;
import ru.timutkin.restfulapplication.web.constant.ResponseConstant;
import ru.timutkin.restfulapplication.web.response.book.BookResponse;
import ru.timutkin.restfulapplication.web.response.book.BookWithAuthorIdResponse;
import ru.timutkin.restfulapplication.web.response.book.BookWithAuthorsResponse;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import java.util.*;

@Service
@AllArgsConstructor
public class IBookService implements BookService {

    BookRepository bookRepository;

    BookMapper bookMapper;

    AuthorMapper authorMapper;

    AuthorRepository authorRepository;

    EntityManager entityManager;

    private static final Integer PAGE_SIZE = 10;

    @Transactional
    @Override
    public BookWithAuthorIdResponse createBookWithoutAuthors(BookDTO bookDTO) {
        if (bookRepository.existsByTitleAndYearOfPrinting(bookDTO.getTitle(), bookDTO.getYearOfPrinting())){
            throw new DataAlreadyExistsException(ResponseConstant.BOOK_ALREADY_EXISTS);
        }
        BookEntity bookEntity = bookMapper.bookDtoToBookEntity(bookDTO);
        bookRepository.save(bookEntity);
        return BookWithAuthorIdResponse.builder()
                .bookDTO(bookMapper.bookEntityToBookDto(bookEntity))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public BookDTO getBookById(Long id) {
        Optional<BookEntity> mayBeBook = bookRepository.findById(id);

        if (mayBeBook.isEmpty()){
            throw new BookNotFoundException(String.format(ResponseConstant.BOOK_WITH_ID_D_NOT_FOUND,id));
        }

        return bookMapper.bookEntityToBookDto(mayBeBook.get());
    }

    @Override
    @Transactional
    public void deleteBookById(Long id) throws BookNotFoundException {
        if (!bookRepository.existsById(id)){
            throw new BookNotFoundException(String.format(ResponseConstant.BOOK_WITH_ID_D_NOT_FOUND,id));
        }
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateBook(BookDTO bookDTO) {
        Optional<BookEntity> bookEntity = bookRepository.findById(bookDTO.getId());
        if (bookEntity.isEmpty()){
            throw new BookNotFoundException(String.format(ResponseConstant.BOOK_WITH_ID_D_NOT_FOUND,bookDTO.getId()));
        }
        BookEntity updatedBookEntity = bookMapper.bookDtoToBookEntity(bookDTO);
        updatedBookEntity.setAuthors(bookEntity.get().getAuthors());
        bookRepository.save(updatedBookEntity);
    }

    @Override
    @Transactional
    public BookWithAuthorIdResponse createBookWithAuthors(BookDTO bookDTO, List<AuthorDTO> authorDTOList) {
        if (bookRepository.existsByTitleAndYearOfPrinting(bookDTO.getTitle(), bookDTO.getYearOfPrinting())){
            throw new DataAlreadyExistsException(ResponseConstant.BOOK_ALREADY_EXISTS);
        }
        BookWithAuthorIdResponse response = new BookWithAuthorIdResponse();
        BookEntity bookEntity = bookMapper.bookDtoToBookEntity(bookDTO);
        bookEntity.setAuthors(new HashSet<>());

        authorDTOList.stream()
                .map(authorMapper::authorDtoToEntity)
                .filter(authorEntity -> authorEntity.getId() == null)
                .peek(authorEntity -> authorEntity.setBooks(new HashSet<>()))
                .peek(authorRepository::save)
                .peek(authorEntity-> response.addAuthorById(authorEntity.getId()))
                .forEach(bookEntity::addAuthor);

        bookRepository.save(bookEntity);

        authorDTOList.stream()
                .map(authorMapper::authorDtoToEntity)
                .filter(authorEntity -> authorEntity.getId() != null)
                .peek(authorEntity -> response.addAuthorById(authorEntity.getId()))
                .map(authorEntity -> authorRepository.getAuthorEntityById(authorEntity.getId()))
                .forEach(bookEntity::addAuthor);

        bookRepository.saveAndFlush(bookEntity);

        response.setBookDTO(bookMapper.bookEntityToBookDto(bookEntity));

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public BookResponse getFullDataBookById(Long id) {
        EntityGraph<BookEntity> graph = entityManager.createEntityGraph(BookEntity.class);
        graph.addAttributeNodes("authors");
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", graph);
        BookEntity book = entityManager.find(BookEntity.class, id, properties);
        if (book == null){
            throw new BookNotFoundException(String.format(ResponseConstant.BOOK_WITH_ID_D_NOT_FOUND,id));
        }
        return BookWithAuthorsResponse.builder()
                .bookDTO(bookMapper.bookEntityToBookDto(book))
                .authorDTOList(
                        book.getAuthors().stream()
                                .map(authorMapper::authorEntityToAuthorDto)
                                .toList()
                )
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getListOfBooks(Integer numberOfPage) {
        PageRequest pageRequest = PageRequest.of(numberOfPage-1, PAGE_SIZE, Sort.by("title"));
        if (numberOfPage<1){
            throw new IncorrectDataException("Number of page should be more 0");
        }
        return bookRepository.findAll(pageRequest).stream()
                .map(bookMapper::bookEntityToBookDto)
                .toList();
    }


}
