package ru.timutkin.restfulapplication.entity;

import lombok.*;
import ru.timutkin.restfulapplication.enumeration.GenreOfLiterature;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
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

    @ManyToMany
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id")
    )
    private Set<AuthorEntity> authors = new HashSet<>();

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "genre_literature")
    private GenreOfLiterature genreOfLiterature;

    @Column(name = "number_parts")
    private Integer numberOfParts;

    @Column(name = "year_print")
    private Integer yearOfPrinting;

    @Column(name = "count_page" )
    private Integer countOfPage;

    @ManyToMany(mappedBy = "listOfBooks")
    Set<UserEntity> users;

    public void addAuthor(AuthorEntity author){
        authors.add(author);
        author.getBooks().add(this);
    }

    public void addAuthors(Set<AuthorEntity> authors){
        authors.forEach(this::addAuthor);
    }
}
