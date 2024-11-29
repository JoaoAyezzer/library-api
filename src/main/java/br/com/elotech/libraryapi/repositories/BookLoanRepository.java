package br.com.elotech.libraryapi.repositories;

import br.com.elotech.libraryapi.models.BookLoan;
import org.springframework.stereotype.Repository;

@Repository
public interface BookLoanRepository extends BaseRepository<BookLoan> {
}
