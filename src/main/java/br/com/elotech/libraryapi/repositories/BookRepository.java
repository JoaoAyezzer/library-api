package br.com.elotech.libraryapi.repositories;

import br.com.elotech.libraryapi.models.Book;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends BaseRepository<Book> {

    List<Book> findByCategoryIdInAndIdNotIn(Collection<UUID> categoryIds, Collection<UUID> ids);
}
