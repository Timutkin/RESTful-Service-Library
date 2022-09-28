package ru.timutkin.restfulapplication.service.iservice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.timutkin.restfulapplication.dto.AuthorDTO;
import ru.timutkin.restfulapplication.exception.IncorrectDataException;
import ru.timutkin.restfulapplication.repository.AuthorRepository;
import ru.timutkin.restfulapplication.service.AuthorService;

@AllArgsConstructor
@Service
public class IAuthorService implements AuthorService {

    AuthorRepository authorRepository;

    @Override
    public Long createAuthor(AuthorDTO authorDTO) throws IncorrectDataException {
        System.out.println(authorDTO);
        return null;
    }
}
