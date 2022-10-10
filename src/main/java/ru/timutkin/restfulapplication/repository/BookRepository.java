package ru.timutkin.restfulapplication.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import ru.timutkin.restfulapplication.entity.BookEntity;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    Optional<BookEntity> findById(Long id);

    void deleteById(Long id);

    boolean existsById(Long id);

    BookEntity getBookEntityById(Long id);

    boolean existsByTitle(String title);

    boolean existsByTitleAndYearOfPrinting(String title, Integer yearOfPrinting);

    List<BookEntity> findAll(Pageable pageable);

}