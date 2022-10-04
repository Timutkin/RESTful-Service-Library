package ru.timutkin.restfulapplication.entity;

import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "author", schema = "public")
public class AuthorEntity {

    @Id
    @SequenceGenerator(name = "author_seq",
            sequenceName = "author_sequence", allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq")
    Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String patronymic;
    @Column(name = "year_birth")
    private LocalDate yearOfBirth;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    Set<BookEntity> books = new HashSet<>();

    public void addBook(BookEntity book){
        books.add(book);
        book.getAuthors().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof AuthorEntity)) return false;

        AuthorEntity that = (AuthorEntity) o;

        return new EqualsBuilder()
                .append(getFirstName(), that.getFirstName())
                .append(getLastName(), that.getLastName())
                .append(getPatronymic(), that.getPatronymic())
                .append(getYearOfBirth(), that.getYearOfBirth()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getFirstName())
                .append(getLastName())
                .append(getPatronymic())
                .append(getYearOfBirth())
                .toHashCode();
    }
}
