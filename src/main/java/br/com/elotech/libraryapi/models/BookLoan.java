package br.com.elotech.libraryapi.models;

import br.com.elotech.libraryapi.enums.StatusLoan;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "book_loans")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookLoan extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private StatusLoan status;

    @Column(nullable = false)
    private LocalDate returnDate;
}
