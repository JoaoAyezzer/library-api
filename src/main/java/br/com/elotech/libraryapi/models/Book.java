package br.com.elotech.libraryapi.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book extends BaseEntity{

    @Column(nullable = false)
    private String title;

    @ElementCollection
    @CollectionTable(name = "isbns")
    private Set<String> isbns;

    @ElementCollection
    @CollectionTable(name = "authors")
    private Set<String> authors;

    @Column(nullable = false)
    private LocalDate publishedDate;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false, referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "book")
    private List<BookLoan> bookLoans;



}
