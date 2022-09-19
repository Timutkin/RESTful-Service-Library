package ru.timutkin.restfulapplication.service.iservice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.timutkin.restfulapplication.dto.BookDTO;
import ru.timutkin.restfulapplication.entity.BookEntity;
import ru.timutkin.restfulapplication.exception.BookNotFoundException;
import ru.timutkin.restfulapplication.mapper.BookMapper;
import ru.timutkin.restfulapplication.repository.BookRepository;
import ru.timutkin.restfulapplication.service.BookService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class IBookService implements BookService {

    BookRepository bookRepository;

    BookMapper bookMapper;

    @Transactional
    @Override
    public Long createBook(BookDTO bookDTO) {
        BookEntity bookEntity = bookMapper.bookDtoToBookEntity(bookDTO);
        bookRepository.save(bookEntity);
        return bookEntity.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public BookDTO getBookById(Long id) {
        Optional<BookEntity> mayBeBook = bookRepository.findById(id);

        if (!mayBeBook.isPresent()){
            throw new BookNotFoundException(String.format("Book with id = %d not found",id));
        }

        return bookMapper.bookEntityToBookDto(mayBeBook.get());
    }
}
