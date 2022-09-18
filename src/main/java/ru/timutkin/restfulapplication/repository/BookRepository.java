package ru.timutkin.restfulapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.timutkin.restfulapplication.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}