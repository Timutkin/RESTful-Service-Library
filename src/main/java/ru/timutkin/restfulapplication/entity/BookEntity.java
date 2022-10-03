package ru.timutkin.restfulapplication.entity;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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

    @Enumerated(EnumType.STRING)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof BookEntity)) return false;

        BookEntity that = (BookEntity) o;

        return new EqualsBuilder().append(getTitle(), that.getTitle()).append(getGenreOfLiterature(), that.getGenreOfLiterature()).append(getNumberOfParts(), that.getNumberOfParts()).append(getYearOfPrinting(), that.getYearOfPrinting()).append(getCountOfPage(), that.getCountOfPage()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getTitle()).append(getGenreOfLiterature()).append(getNumberOfParts()).append(getYearOfPrinting()).append(getCountOfPage()).toHashCode();
    }
}
