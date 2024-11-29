package br.com.elotech.libraryapi.repositories;

import br.com.elotech.libraryapi.models.Book;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends BaseRepository<Book> {
}
