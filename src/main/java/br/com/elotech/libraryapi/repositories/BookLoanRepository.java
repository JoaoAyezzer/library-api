package br.com.elotech.libraryapi.repositories;

import br.com.elotech.libraryapi.enums.StatusLoan;
import br.com.elotech.libraryapi.models.BookLoan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookLoanRepository extends BaseRepository<BookLoan> {

    List<BookLoan> findAllByUserId(UUID id);

    List<BookLoan> findAllByBookIdAndStatus(UUID bookId, StatusLoan status);
}
