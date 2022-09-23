package ru.timutkin.restfulapplication.entity;

import lombok.*;

import javax.persistence.*;
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

    private int yearOfBirth;

    @OneToMany(mappedBy = "author")
    Set<BookEntity> books;

}
