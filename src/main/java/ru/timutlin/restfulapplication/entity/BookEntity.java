package ru.timutlin.restfulapplication.entity;

import lombok.*;

import javax.persistence.*;

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

    @Column(name = "year_print")
    private int yearOfPrinting;

    @Column(name = "count_page" )
    private int countOfPage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    UserEntity userEntity;
}
