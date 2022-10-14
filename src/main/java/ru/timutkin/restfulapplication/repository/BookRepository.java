package ru.timutkin.restfulapplication.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.timutkin.restfulapplication.entity.BookEntity;

import java.util.List;
import java.util.Optional;

@Repository

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    Optional<BookEntity> findById(Long id);

    void deleteById(Long id);

    boolean existsById(Long id);

    BookEntity getBookEntityById(Long id);

    boolean existsByTitle(String title);

    List<BookEntity> findByTitleLikeIgnoreCase(String title);

    boolean existsByTitleAndYearOfPrinting(String title, Integer yearOfPrinting);


}