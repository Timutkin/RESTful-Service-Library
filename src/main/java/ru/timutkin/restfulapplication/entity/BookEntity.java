package ru.timutkin.restfulapplication.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "book", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookEntity {
    @Id
    @SequenceGenerator(name = "book_seq",
            sequenceName = "paste_sequence", allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    private Long id;

    private String title;

    private String author;

    @Column(name = "number_parts")
    private Integer numberOfParts;

    @Column(name = "year_print")
    private Integer yearOfPrinting;

    @Column(name = "count_page" )
    private Integer countOfPage;

    @ManyToMany(mappedBy = "listOfBooks")
    Set<UserEntity> users;
}
