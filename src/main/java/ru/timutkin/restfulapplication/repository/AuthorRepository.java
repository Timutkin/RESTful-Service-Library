package ru.timutkin.restfulapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.timutkin.restfulapplication.entity.AuthorEntity;

import java.time.LocalDate;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

    AuthorEntity getAuthorEntityById(Long id);

    boolean existsByFirstNameAndAndLastNameAndAndPatronymicAndYearOfBirth(String firstName, String lastName, String patronymic, LocalDate date);

    boolean existsById(Long id);

}