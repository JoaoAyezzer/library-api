package br.com.elotech.libraryapi.models;

import br.com.elotech.libraryapi.enums.StatusLoan;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    @JoinColumn(name = "book_id")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private StatusLoan statusLoan;
    private LocalDate returnDate;
}
